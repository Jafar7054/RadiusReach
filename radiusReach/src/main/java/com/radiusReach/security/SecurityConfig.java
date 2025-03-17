package com.radiusReach.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.radiusReach.service.UserConfigurationDetailsService;


/**
 * Configuration class for application security settings.
 */
@Configuration //Tells java that this class holds important security configuration
@EnableWebSecurity //Activates spring security for our app
public class SecurityConfig {
	
	@Autowired
	UserConfigurationDetailsService userConfigurationDetailsService;
	/**
     * Configures the security filter chain for HTTP requests.
     * @param http the HttpSecurity configuration.
     * @return the configured security filter chain.
     * @throws Exception if an error occurs during configuration.
     */
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable() // Disable CSRF for simplicity; enable in production with proper configuration
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/","/login","/signup","/oauth2/authorization/google").permitAll() // Publicly accessible
	                .requestMatchers("/resources/**", "/templates/**", "/css/**", "/js/**").permitAll() // Static resources
	                .anyRequest().authenticated() // All other requests require authentication
	            )
	            .formLogin(form -> form
	                .loginPage("/") // Set custom login page
	               // .loginProcessingUrl("/login") // Default endpoint for login form submission
	               // .defaultSuccessUrl("/login",true) // Redirect here after successful login
	                .permitAll()
	            )
	            .oauth2Login(oauth -> oauth
	                .loginPage("/") // Custom login page for OAuth2
	                .defaultSuccessUrl("/loggedIn") // Redirect here after successful OAuth2 login
					)
	            .logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/")
	                .permitAll()
	            );

	        return http.build();
	    }

	 
	 	/**
	     * Provides a password encoder for secure password storage.
	     * @return the BCrypt password encoder.
	     */
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // Password encoder for form-based login
	    }	    

	    /**
	     * Provides a custom OAuth2 user service for handling user authentication.
	     * @return the custom OAuth2 user service.
	     */
	    @Bean
		public CustomOAuth2UserService customOAuth2UserService()
		{
			return new CustomOAuth2UserService();
		}
	    
	    @Bean
	    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	        return http.getSharedObject(AuthenticationManagerBuilder.class)
	                .userDetailsService(userConfigurationDetailsService)
	                .passwordEncoder(passwordEncoder())
	                .and().build();
	    }

	
	
}
	

