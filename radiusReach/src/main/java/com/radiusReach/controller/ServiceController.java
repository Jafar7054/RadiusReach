package com.radiusReach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radiusReach.entities.Services;
import com.radiusReach.entities.User;
import com.radiusReach.service.CustomBookingService;
import com.radiusReach.service.CustomServiceDetailsService;
import com.radiusReach.service.CustomUserDetailsService;

import customException.CustomDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for handling service-related requests.
 * This includes adding, viewing, and managing services provided by the user.
 */
@Controller
public class ServiceController {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	CustomServiceDetailsService customServiceDetailsService;
	
	@Autowired
	CustomBookingService customBookingService;
	
	
	
	/**
     * Forwards the logged-in user to the page displaying their provided services.
     * 
     * @param request HttpServletRequest for any parameters if needed.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the user services view or else error view if any error occures.
     */
	@RequestMapping("/forwardToMyServices")
	public String forwardToMyServices(HttpServletRequest request, Model model, HttpSession session) {
		
		try {
		User user = (User) session.getAttribute("user");
		List<Services> ser = user.getServices();
		model.addAttribute("userServices", ser);
		return "userServices";
		}
		catch(NullPointerException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	/**
     * Forwards the user to the add service page where they can add a new service.
     * 
     * @param request HttpServletRequest for any parameters if needed.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the add service view.
     */
	@RequestMapping("/forwardToAddService")
	public String forwardToAddService(HttpServletRequest request, Model model, HttpSession session) {

		return "addService";
	}

	/**
     * Handles the submission of a new service form, adds the service to the
     * database, and updates the list of services provided by the logged-in user.
     * 
     * @param request       HttpServletRequest containing the service form data.
     * @param model         Model to add attributes for the view.
     * @param authentication Authentication object containing OAuth2 user details.
     * @return the user services view after adding the new service or else error view if any error occures.
     */
	@PostMapping("/addService")
	public String addService(HttpServletRequest request, Model model, Authentication authentication) {
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		String email = oauth2User.getAttribute("email");
		User user=null;
		try {
			user = customUserDetailsService.findByEmail(email);
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			String description = request.getParameter("description");
			String address = request.getParameter("address1") + request.getParameter("address2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			int pincode = Integer.parseInt(request.getParameter("pincode"));
			long phoneNumber = Long.parseLong(request.getParameter("phno"));
			Services service = new Services(name, price, description, address, city, state, pincode, phoneNumber, user);
			customServiceDetailsService.addService(service);
			List<Services> ser=customServiceDetailsService.getByUserId(user.getId());
			model.addAttribute("userServices", ser);
			return "userServices";
		} catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}
	
	
}
