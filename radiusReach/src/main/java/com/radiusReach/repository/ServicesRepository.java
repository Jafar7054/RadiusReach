package com.bookYouServices.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookYouServices.entities.Services;

import jakarta.persistence.EntityNotFoundException;

/**
 * Repository interface for managing Services entities with custom query methods.
 */
public interface ServicesRepository extends JpaRepository<Services,Long>{
	
	/**
     * Finds services by pincode excluding those provided by the given user.
     * @param pincode the service pincode.
     * @param userId the ID of the user to exclude.
     * @return a list of services matching the criteria.
     * @throws DataAccessException if any database error occurs.
     */
	@Query(value="select * from services where pincode=:pincode and vendor_id!=:userId",nativeQuery=true)
	public List<Services> findByPincode(@Param("pincode")int pincode, @Param("userId")long userId) throws DataAccessException;
	
	 /**
     * Finds all services excluding those provided by the given user.
     * @param userId the ID of the user to exclude.
     * @return a list of services not provided by the user.
     * @throws DataAccessException if any database error occurs.
     */
	@Query(value="select * from services where vendor_id!=:userId",nativeQuery=true)
	public List<Services> findAllExceptUser(@Param("userId")long userId) throws DataAccessException;
	
	/**
     * Finds services by vendor ID.
     * @param userId the vendor ID.
     * @return a list of services provided by the vendor.
     * @throws DataAccessException if any database error occurs.
     */
	public List<Services> findByVendorId(long userId) throws DataAccessException;
	
	/**
     * Finds a service by its ID.
     * @param id the ID of the service.
     * @return the service if found.
     * @throws EntityNotFoundException if the service is not found.
     */
	public Services findById(long id) throws EntityNotFoundException;
	

}
