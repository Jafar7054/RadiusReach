package com.radiusReach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.radiusReach.entities.Services;
import com.radiusReach.entities.User;
import com.radiusReach.entities.UserAdditionalDetails;
import com.radiusReach.repository.UserAdditionalDetailsRepository;

import customException.CustomDatabaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing additional user details.
 */
@Service
@Slf4j
public class CustomAdditionalDetailsService {

	@Autowired
	UserAdditionalDetailsRepository userAdditionalDetailsRepository;
	
	
	@Autowired
	CustomServiceDetailsService customServiceDetailsService;
	
	/**
     * Retrieves the pincode for a user by their ID.
     * @param id the ID of the user.
     * @return the user's pincode.
	 * @throws CustomDatabaseException 
     */
	public int getPincodeById(long id) throws CustomDatabaseException
	{
		try {
		return userAdditionalDetailsRepository.findPincodeByUserId(id);
		}
		catch(RuntimeException e)
		{
			log.error("An error occured while trying to fetch pincode of user.");
			throw new CustomDatabaseException("Unable to fetch pincode of user",e);
		}
	}
	
	/**
     * Retrieves additional details for a user by their ID.
     * @param id the ID of the user.
     * @return the user's additional details.
	 * @throws CustomDatabaseException 
     */
	public UserAdditionalDetails getById(long id) throws CustomDatabaseException
	{
		try {
		return userAdditionalDetailsRepository.findByUserId(id);
		}
		catch(RuntimeException e)
		{
			log.error("An error occured while trying to fetch additional details of user.");
			throw new CustomDatabaseException("Unable to fetch details of user.",e);
		}
	}
	
	 /**
     * Saves or updates additional user details.
     * @param addOn the additional details to save.
	 * @throws CustomDatabaseException 
     */
	public void saveAddOn(UserAdditionalDetails addOn) throws CustomDatabaseException
	{
		try {
			userAdditionalDetailsRepository.save(addOn);
			log.info("user added successfully.");
		}
		catch(RuntimeException e)
		{
			log.error("An error occured while trying to add additional details of user into database.");
			throw new CustomDatabaseException("Unable to add user's additional details",e);
		}
	}
	
	/**
     * Updates the dashboard with user and additional details.
     * @param model the model to update.
     * @param user the user object.
     * @param addOn the additional details object.
     * @return the updated model.
	 * @throws CustomDatabaseException 
     */
	public Model updateDashboard(Model model, User user, UserAdditionalDetails addOn) throws CustomDatabaseException {

		log.info("Updating dashboard");
		List<Services> ser = customServiceDetailsService.getByPincode(addOn.getPincode(), user.getId());
		model.addAttribute("usableServices", ser);
		return model;
	}
}
