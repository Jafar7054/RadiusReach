package com.radiusReach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radiusReach.entities.User;
import com.radiusReach.entities.UserAdditionalDetails;
import com.radiusReach.service.CustomAdditionalDetailsService;

import customException.CustomDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for handling user profile-related requests.
 * This includes managing additional user details and updating the dashboard.
 */
@Controller
public class UserProfileController {

	@Autowired
	CustomAdditionalDetailsService customAdditionalDetailsService;
	
	/**
     * Handles the submission of additional user details, such as phone number,
     * address, city, and pincode. Saves the details and updates the dashboard.
     * 
     * @param request HttpServletRequest containing form data for additional details.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the home view after saving the additional details or else error view if error occures.
     */
	@RequestMapping("/additionalDetails")
	public String additionalDetails(HttpServletRequest request, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		long phno = Long.parseLong(request.getParameter("phno"));
		String address = request.getParameter("address1") + request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		UserAdditionalDetails userAdditionalDetails = new UserAdditionalDetails(address, city, state, pincode, phno, user);
		try {
			customAdditionalDetailsService.saveAddOn(userAdditionalDetails);
		System.out.println("going to update...");
		model = customAdditionalDetailsService.updateDashboard(model, user, userAdditionalDetails);
		return "home";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	/**
     * Forwards the user to the dashboard page after fetching their additional
     * details.
     * 
     * @param model   Model to add attributes for the view.
     * @param session HttpSession containing the logged-in user.
     * @return the home view or else error view in case of error.
     */
	@RequestMapping("/forwardToDashboard")
	public String forwardToDashboard(Model model, HttpSession session) {
		UserAdditionalDetails userAdditionalDetails = (UserAdditionalDetails) session.getAttribute("addOn");
		User user = (User) session.getAttribute("user");
		try {
		model = customAdditionalDetailsService.updateDashboard(model, user, userAdditionalDetails);
		return "home";
		}
		catch(CustomDatabaseException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
}
