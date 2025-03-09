package com.bookYouServices.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bookYouServices.entities.Booking;
import com.bookYouServices.entities.Services;

import jakarta.persistence.EntityNotFoundException;

/**
 * Repository interface for managing Booking entities with custom query methods.
 */
public interface BookingRepository extends JpaRepository<Booking, Long>
{
	 /**
     * Finds services booked by a specific user.
     * @param userId the ID of the user.
     * @return a list of services booked by the user.
     * @throws DataAccessException if any database error occures.
     */
	 @Query("SELECT b.service FROM Booking b WHERE b.user.id = :userId") 
	    List<Services> findServicesBookedByUserId(@Param("userId") Long userId) throws DataAccessException;
	 
	 /**
	     * Finds user bookings based on the date, user ID, and job status.
	     * @param currentDate the current date.
	     * @param userId the ID of the user.
	     * @param status the job status.
	     * @return a list of bookings matching the criteria.
	     * @throws DataAccessException if any database error occures. 
	     */
	 @Query("SELECT b FROM Booking b WHERE b.date >= :currentDate AND b.user.id = :userId and jobStatus = :status")
	    List<Booking> findUserBookings(@Param("currentDate") String currentDate,@Param("userId") Long userId, @Param("status") String status) throws DataAccessException;
	 
	 /**
	     * Finds pending bookings for a vendor based on the date and request status.
	     * @param currentDate the current date.
	     * @param userId the ID of the vendor.
	     * @param status the request status.
	     * @return a list of pending bookings.
	     * @throws DataAccessException if any database error occures.
	     */
	 @Query("SELECT b FROM Booking b WHERE b.date >= :currentDate AND b.service.vendor.id = :userId and requestStatus = :status")
	    List<Booking> findPendingBookings(@Param("currentDate") String currentDate,@Param("userId") Long userId,@Param("status") String status) throws DataAccessException;
	 
	 /**
	     * Finds scheduled services for a vendor based on the date and request status.
	     * @param currentDate the current date.
	     * @param userId the ID of the vendor.
	     * @param status the request status.
	     * @return a list of scheduled services.
	     * @throws DataAccessException if any database error occures.
	     */
	 @Query("SELECT b FROM Booking b WHERE b.date >= :currentDate AND b.service.vendor.id = :userId and requestStatus = :status")
	    List<Booking> findScheduledServices(@Param("currentDate") String currentDate,@Param("userId") Long userId,@Param("status") String status) throws DataAccessException;
	 
	 /**
	     * Updates the request status of a booking.
	     * @param status the new request status.
	     * @param bookingId the ID of the booking to update.
	     * @throws DataAccessException if any database error occures.
	     */
	 @Modifying
	 @Transactional
	 @Query("UPDATE Booking b SET b.requestStatus = :status WHERE b.id = :bookingId")
	    void updateBookingRequestStatus(@Param("status") String status, @Param("bookingId") Long bookingId)throws DataAccessException;
	 
	 @Query("SELECT b FROM Booking b WHERE b.date < :currentDate AND b.user.id = :userId")
	 	List<Booking> findPreviousBookingsOfUser(@Param("currentDate") String currentDate,@Param("userId") Long userId) throws DataAccessException;

	
}
