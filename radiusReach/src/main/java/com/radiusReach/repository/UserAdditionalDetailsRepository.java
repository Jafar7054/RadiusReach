package com.bookYouServices.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookYouServices.entities.UserAdditionalDetails;

import jakarta.persistence.EntityNotFoundException;

/**
 * Repository interface for managing UserAdditionalDetails entities with JPA query methods.
 */
public interface UserAdditionalDetailsRepository extends JpaRepository<UserAdditionalDetails, Long>{

	/**
     * Finds pincode of the user.
     * @param userId the user's ID.
     * @return an integer value containing pincode of user.
     * @throws DataAccessException if any database error occurs.
     */
	int findPincodeByUserId(Long userId) throws DataAccessException;
	
	/**
     * Finds user's additional details by the provided user ID.
     * @param userId the ID of the user.
     * @return UserAdditionalDetails object.
     * @throws DataAccessException if any database error occurs.
     */
	UserAdditionalDetails findByUserId(Long userId) throws EntityNotFoundException;
}
