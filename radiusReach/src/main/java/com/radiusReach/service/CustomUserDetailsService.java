package com.radiusReach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.radiusReach.entities.User;
import com.radiusReach.repository.UserrRepository;

import com.radiusReach.customException.CustomDatabaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing user details.
 */
@Service
@Slf4j
public class CustomUserDetailsService {

    @Autowired
    private UserrRepository userRepository;

    /**
     * Retrieves the user by their email and password.
     * @param email the email of the user.
     * @param password the password of user.
     * @return the user.
	 * @throws CustomDatabaseException 
     */
    public User findByEmailAndPassword(String email,String password) throws CustomDatabaseException
    {
    	try {
    	return userRepository.findByEmailAndPassword(email, password);
    	}
    	catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch uder based on the provide email and password. Error message: ",exception);
    		throw new CustomDatabaseException("User not found",exception);
    	}
    }
    
    /**
     * Retrieves the user by their email.
     * @param email the email of the user.
     * @return the user.
	 * @throws CustomDatabaseException 
     */
    public User findByEmail(String email) throws CustomDatabaseException
    {
    	try {
    	return userRepository.findByEmail(email);
    	}
    	catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch uder based on the provide google email. Error message: ",exception);
    		throw new CustomDatabaseException("User not found",exception);
    	}
    }
    
    /**
     * Saves user into database..
     * @param user the user.
	 * @throws CustomDatabaseException 
     */
    public void saveUser(User user) throws CustomDatabaseException 
    {
    	try {
    	userRepository.save(user);
    	log.info("User added successfully.");
    	}
    	catch(RuntimeException exception)
    	{
    		log.error("Unable to add User to the database. Error message: ",exception);
    		throw new CustomDatabaseException("User not added",exception);
    	}
	}
    
}