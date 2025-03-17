package com.radiusReach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.radiusReach.entities.Services;
import com.radiusReach.repository.ServicesRepository;

import customException.CustomDatabaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing services.
 */
@Service
@Slf4j
public class CustomServiceDetailsService {

	@Autowired
	ServicesRepository serviceRepository;
	
	/**
     * Retrieves the list of services booked excluding those provided by user.
     * @param userId the ID of the user.
     * @return a list of services;
	 * @throws CustomDatabaseException 
     */
	public List<Services> getAllExceptUser(long userId) throws CustomDatabaseException
	{
		try {
		return serviceRepository.findAllExceptUser(userId);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch services based on the pincode except those provided by user. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch services",exception);
    	}
	}
	
	/**
     * Retrieves the list of services available in an area of the user excluding those provided by user.
     * @param pincode of the user
     * @param userId the ID of the user.
     * @return a list of services;
	 * @throws CustomDatabaseException 
     */
	public List<Services> getByPincode(int pincode,long userId) throws CustomDatabaseException
	{
		try {
		return serviceRepository.findByPincode(pincode, userId);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch services based on the pincode. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch services",exception);
    	}
	}
	
	/**
     * Adds new service provided by user into database.
     * @param service the SERVICE.
	 * @throws CustomDatabaseException 
     */
	public void addService(Services service) throws CustomDatabaseException
	{
		try {
		serviceRepository.save(service);
		log.info("Service added successfully.");
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to add service. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to add service",exception);
    	}
	}
	
	/**
     * Retrieves the list of services provided by the user.
     * @param userId the ID of the user.
     * @return a list of services;
	 * @throws CustomDatabaseException 
     */
	public List<Services> getByUserId(long userId) throws CustomDatabaseException
	{
		try {
		return serviceRepository.findByVendorId(userId);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch services provided by user. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch services provided by user",exception);
    	}
	}
	
	/**
     * Retrieves the service by service id.
     * @param serviceId the ID of the service.
     * @return the service;
	 * @throws CustomDatabaseException 
     */
	public Services getById(long serviceId) throws CustomDatabaseException
	{
		try {
		return serviceRepository.findById(serviceId);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch service by id "+serviceId+". Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch service by Id",exception);
    	}
	}
}
