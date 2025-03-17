package com.radiusReach.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.radiusReach.entities.User;
import com.radiusReach.repository.UserrRepository;

@Service
public class UserConfigurationDetailsService implements UserDetailsService{

	@Autowired
	UserrRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(username);
		System.out.println(user.getUserName());
		if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(),
                getAuthorities(user));
		
	}

	 private Collection<? extends GrantedAuthority> getAuthorities(User user) {
	        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        // Add roles or permissions here if needed
	        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	        return authorities;
	    }
}
