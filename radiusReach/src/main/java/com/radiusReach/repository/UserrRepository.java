package com.bookYouServices.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookYouServices.entities.User;

/**
 * Repository interface for managing User entities with JPA query methods.
 */
public interface UserrRepository extends JpaRepository<User, Long>{

	/**
     * Finds user by email .
     * @param email email of the user.
     * @return a User object.
     * @throws DataAccessException if any database error occurs.
     */
	public User findByEmail(String email) throws DataAccessException;
	
	/**
     * Finds user by email and password.
     * @param email the email of the user.
     * @param password the password of the user 
     * @return a User object.
     * @throws DataAccessException if any database error occurs.
     */
	public User findByEmailAndPassword(String email, String password) throws DataAccessException;
}
