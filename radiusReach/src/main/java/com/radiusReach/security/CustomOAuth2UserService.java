package com.bookYouServices.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bookYouServices.entities.User;
import com.bookYouServices.repository.UserrRepository;


/**
 * Custom implementation of OAuth2UserService for managing user authentication and registration.
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserrRepository usersRepository;

    /**
     * Loads user details from an OAuth2 provider and manages user registration or retrieval.
     * @param userRequest the OAuth2 user request.
     * @return the loaded OAuth2 user.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // Extract user details from Google
        Map<String, Object> attributes = oauth2User.getAttributes();
        String username = (String) attributes.get("email"); // Use the email as the username
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
       
        // Check if the user already exists
        User user = usersRepository.findByEmail(username);
        if (user == null) {
            // If not, create and save a new user
            user = new User();
            user.setEmail(username);
            user.setUserName(firstName+lastName);
            
            usersRepository.save(user);
        }

        // Store the user in the SecurityContext for future requests
        return oauth2User;
    }
}
