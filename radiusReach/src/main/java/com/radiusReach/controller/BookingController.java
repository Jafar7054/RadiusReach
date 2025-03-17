package com.radiusReach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radiusReach.entities.Booking;
import com.radiusReach.entities.Services;
import com.radiusReach.entities.User;
import com.radiusReach.service.CustomBookingService;
import com.radiusReach.service.CustomServiceDetailsService;

import customException.CustomDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for handling booking-related requests.
 * This includes booking a service, updating the status, and managing requests.
 */
@Controller
public class BookingController {
	
	@Autowired
	CustomBookingService customBookingService;
	
	@Autowired
	CustomServiceDetailsService customServiceDetailsService;
	
	/**
     * Forwards the user to the booking form for a selected service.
     * 
     * @param request HttpServletRequest containing the selected service details.
     * @param model   Model to add attributes for the view.
     * @return the booking form view.
     */
	@PostMapping("/forwardToBookingForm")
	public String bookingForm(HttpServletRequest request, Model model) {
		Long serviceId = Long.parseLong(request.getParameter("serviceId"));
		Services service=null;
		try {
			service = customServiceDetailsService.getById(serviceId);
			model.addAttribute("serviceBooked", service);
			return "bookingForm";
		} catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}

	/**
     * Handles the booking submission for a service, saves the booking details,
     * and forwards the user to their bookings page.
     * 
     * @param request HttpServletRequest containing the booking form data.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the my bookings view after saving the booking.
     */
	@PostMapping("/book")
	public String booking(HttpServletRequest request, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Long serviceId = Long.parseLong(request.getParameter("serviceId"));
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		Services service=null;
		try {
			service = customServiceDetailsService.getById(serviceId);
			Booking booking = new Booking(date, time, "pending", "pending", user, service);
			customBookingService.saveBooking(booking);
			List<Booking> bookings = customBookingService.getUserBookings(user.getId());
			model.addAttribute("bookings", bookings);
			return "myBookings";
		} catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}

	/**
     * Forwards the user to their list of bookings.
     * 
     * @param request HttpServletRequest for any parameters if needed.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the my bookings view.
     */
	@RequestMapping("/forwardToBookings")
	public String forwardToBookings(HttpServletRequest request, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		try {
		List<Booking> bookings = customBookingService.getUserBookings(user.getId());
		model.addAttribute("bookings", bookings);
		return "myBookings";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	/**
     * Forwards the user to their list of service requests.
     * 
     * @param request HttpServletRequest for any parameters if needed.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the requests view.
     */
	@RequestMapping("/forwardToRequests")
	public String forwardToRequests(HttpServletRequest request, Model model, HttpSession session)
	{
		User user = (User) session.getAttribute("user");
		try {
		List<Booking> requests= customBookingService.getRequestedServices(user.getId());
		model.addAttribute("requests", requests);
		return "requests";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	 /**
     * Updates the status of a booking (approve/decline) and refreshes the list of
     * service requests.
     * 
     * @param request HttpServletRequest containing the booking ID and new status.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the requests view after updating the status.
     */
	@PostMapping("/UpdateStatus")
	public String updateStatus(HttpServletRequest request, Model model, HttpSession session)
	{
		Long bookingId=Long.parseLong(request.getParameter("bookingId"));
		String newStatus=request.getParameter("submit");
		User user = (User) session.getAttribute("user");
		try {
			customBookingService.updateStatus(bookingId, newStatus);
		List<Booking> requests=customBookingService.getRequestedServices(user.getId()); 

		model.addAttribute("requests", requests);
		return "requests";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}
	
	
	/**
     * Forwards the user to their list of scheduled services.
     * 
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the scheduled services view.
     */
	@RequestMapping("/forwardToScheduledServices")
	public String forwardToScheduledServices(Model model, HttpSession session,Authentication authentication)
	{
		User user = (User) session.getAttribute("user");
		try {
		List<Booking> requests= customBookingService.getScheduledServices(user.getId());
		
		model.addAttribute("requests", requests);
		return "scheduledServices";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	/**
     * Forwards the user to their list of previous bookings.
     * 
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the scheduled services view.
     */
	@RequestMapping("/forwardToPreviousBookings")
	public String forwardToPreviousBookings(Model model, HttpSession session)
	{
		User user = (User) session.getAttribute("user");
		try {
		List<Booking> previousBookings= customBookingService.previousBookings(user.getId());
		
		model.addAttribute("previousBookings", previousBookings);
		return "previousBookings";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}



}
