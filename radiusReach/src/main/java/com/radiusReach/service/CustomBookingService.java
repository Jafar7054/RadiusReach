package com.bookYouServices.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.bookYouServices.entities.Booking;
import com.bookYouServices.entities.Services;
import com.bookYouServices.repository.BookingRepository;

import customException.CustomDatabaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing bookings.
 */
@Service
@Slf4j
public class CustomBookingService {

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	MailService mailService;
	
	/**
     * Retrieves the list of services booked by the user by their ID.
     * @param id the ID of the user.
     * @return a list of services;
	 * @throws CustomDatabaseException 
     */
	public List<Services> getserviceId(long id) throws CustomDatabaseException
	{
		try {
		return bookingRepository.findServicesBookedByUserId(id);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch services booked by user. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch services booked by user",exception);
    	}
	}
	
	/**
     * saves booking.
     * @param booking the booking entity having booking details.
	 * @throws CustomDatabaseException 
     */
	public void saveBooking(Booking booking) throws CustomDatabaseException
	{
		try {
		bookingRepository.save(booking);
		mailService.sendMailToUser(booking.getUser().getEmail(), booking.getService().getServiceName(), booking.getService().getPhoneNumber(), booking.getUser().getUserName());
		mailService.sendMailToVendor(booking.getService().getVendor().getEmail(), booking.getService().getServiceName(), booking.getService().getVendor().getUserName());
		log.info("booking confirmed for the user:"+booking.getUser().getUserName()+" mail sent to user's email:"+booking.getUser().getEmail()+"as well as vendor's email:"+booking.getService().getVendor().getEmail());
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to save booking made by user. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to save booking made by user",exception);
    	}
		
	}
	
	/**
     * Retrieves the list of bookings booked by the user by their ID.
     * @param userId the ID of the user.
     * @return a list of services;
	 * @throws CustomDatabaseException 
     */
	public List<Booking> getUserBookings(Long userId) throws CustomDatabaseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);

        try {
        return bookingRepository.findUserBookings(currentDate,userId,"pending");
        }
        catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch bookings made by user. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch bookings made by user",exception);
    	}
    }
	
	/**
     * Retrieves the list of bookings requested by the user to the vendor by vendor's ID.
     * @param userId the ID of the vendor.
     * @return a list of bookings;
	 * @throws CustomDatabaseException 
     */
	public List<Booking> getRequestedServices(Long userId) throws CustomDatabaseException
	{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String currentDate = LocalDate.now().format(formatter);
	        
	        try {
	        return bookingRepository.findPendingBookings(currentDate, userId, "pending");
	        }
	        catch(DataAccessException exception)
	    	{
	    		log.error("Unable to get pending booking requests for the vendor. Error message: ",exception);
	    		throw new CustomDatabaseException("Unable to get pending booking requests for the vendor",exception);
	    	}
	}
	
	/**
     * Retrieves the list of bookings scheduled for the vendor by their ID.
     * @param userId the ID of the vendor.
     * @return a list of bookings;
	 * @throws CustomDatabaseException 
     */
	public List<Booking> getScheduledServices(Long userId) throws CustomDatabaseException
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);
        try {
		return bookingRepository.findScheduledServices(currentDate, userId, "approve");
        }
        catch(DataAccessException exception)
    	{
    		log.error("Unable to get list of bookings scheduled for the vendor. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to get list of bookings scheduled for the vendor",exception);
    	}
        
	}
	
	/**
     * Update status of the booking.
     * @param bookingId the booking Id.
     * @param status the updated request status
	 * @throws CustomDatabaseException 
     */
	public void updateStatus(Long bookingId, String status) throws CustomDatabaseException
	{
		try {
		Booking booking=bookingRepository.getById(bookingId);
	    bookingRepository.updateBookingRequestStatus(status, bookingId);
	    mailService.sendMailOnBookingStatusChangeToUser(booking.getUser().getEmail(), booking.getService().getServiceName(), booking.getUser().getUserName());
	    log.info("request status of booking with id:"+bookingId+" has changed, and mail sent to user at:"+booking.getUser().getEmail());
	    if(status.equals("approve"))
	    	mailService.sendMailOnBookingConfirmToVendor(booking.getService().getVendor().getEmail(), booking.getService().getServiceName(), booking.getUser().getUserName(),booking.getDate(),booking.getTime());
			log.info("mail for approving the request is sent to vendor with scheduled date and time at:"+booking.getService().getVendor().getUserName());
		}
        catch(DataAccessException exception)
    	{
    		log.error("Unable to update status of booking. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to update status of booking.",exception);
    	}
	    
	}
	
	/**
     * Retrieve the list of previous bookings 
     * @param userId the ID of the user.
	 * @throws CustomDatabaseException 
     */
	public List<Booking> previousBookings(Long userId) throws CustomDatabaseException
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);
        System.out.println(currentDate);
        try {
		return bookingRepository.findPreviousBookingsOfUser(currentDate, userId);
        }
        catch(DataAccessException exception)
    	{
    		log.error("Unable to get list of bookings scheduled for the vendor. Error message: ",exception);
    		throw new CustomDatabaseException("Unable to get list of bookings scheduled for the vendor",exception);
    	}
	}
	
	/**
     * Retrieve the list of bookings based on booking id. 
     * @param id the ID of the user.
	 * @throws CustomDatabaseException 
     */
	public Booking getBookingById(Long id) throws CustomDatabaseException
	{
		try {
		return bookingRepository.getById(id);
		}
		catch(DataAccessException exception)
    	{
    		log.error("Unable to fetch booking by id: "+id+". Error message: ",exception);
    		throw new CustomDatabaseException("Unable to fetch booking by id.",exception);
    	}
	}
	
}
