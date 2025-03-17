package com.radiusReach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radiusReach.entities.User;
import com.radiusReach.entities.UserAdditionalDetails;
import com.radiusReach.service.CustomAdditionalDetailsService;
import com.radiusReach.service.CustomUserDetailsService;

import customException.CustomDatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


/**
 * Controller for handling authentication-related requests.
 * This includes login, signup, OAuth login, and logout.
 */
@Controller
public class HomeController {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	CustomAdditionalDetailsService customAdditionalService;

	
	/**
     * Displays the login page when navigating to the root URL.
     * 
     * @return login view.
     */
	@RequestMapping("/")
	public String home() {
		System.out.println("going to login page");
		return "login";
	}

	/**
     * Handles login requests by checking the email and password.
     * If credentials are valid, redirects to the home page. Otherwise, shows error.
     * 
     * @param request HttpServletRequest containing login form data.
     * @param model   Model to add attributes for the view.
     * @param session HttpSession for storing the logged-in user.
     * @return the view for home page or login page based on validation.
     */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, HttpSession session) {
		String choice = request.getParameter("submit");
		System.out.println("choice=" + choice);
		if (choice.equals("SignUp"))
			return "signup";
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("uname="+email+" password="+password);
		User u=null;
		try {
			u = customUserDetailsService.findByEmailAndPassword(email, password);
			if (u != null) {
				session.setAttribute("user", u);
				System.out.println(u.getUserName());
				return "home";
			} else {
				model.addAttribute("message", "invalid email or password");
				return "login";
			}
		} catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}

	/**
     * Handles login through OAuth2 providers like Google.
     * Retrieves the OAuth user details and checks if the user exists in the database.
     * If the user doesn't have additional details, redirects to the additional info page.
     * 
     * @param model          Model to add attributes for the view.
     * @param authentication Authentication object containing OAuth2 user details.
     * @param session        HttpSession for storing the logged-in user.
     * @return the view for home page or additional info page based on user status.
     */
	@RequestMapping("/loggedIn")
	public String loginWithGoogle(Model model, Authentication authentication, HttpSession session) {
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		String email = oauth2User.getAttribute("email");
		User u=null;
		try {
			u = customUserDetailsService.findByEmail(email);
			session.setAttribute("user", u);
			UserAdditionalDetails addOn = customAdditionalService.getById(u.getId());
			if (addOn == null)
				return "addtionalInfo";
			else {
				session.setAttribute("addOn", addOn);
				model = customAdditionalService.updateDashboard(model, u, addOn);
				return "home";
		} 
		}catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
		

	}

	 /**
     * Displays the signup page.
     * 
     * @return the signup view.
     */
	@PostMapping("/signup")
	public String signup(HttpServletRequest request, Model model) {
		
		System.out.println("In signup");
		//Retrieving user information from browser 
		String name=request.getParameter("userName");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		long phno = Long.parseLong(request.getParameter("phno"));
		String address = request.getParameter("address1") + request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		
		//Storing user's creddentials into database
		User user=new User(name, email, password);

		UserAdditionalDetails additionalDetails=new UserAdditionalDetails(address, city, state, pincode, phno,user);
		//additionalDetails.setUser(user);
		try {
			customUserDetailsService.saveUser(user);
			customAdditionalService.saveAddOn(additionalDetails);
			System.out.println("added Details");
			model.addAttribute("signUpSuccess", "signUp successfull");
			return "login";
		} catch (CustomDatabaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}

	/**
     * Handles logout requests by invalidating the user session.
     * Redirects the user to the login page.
     * 
     * @param session HttpSession to invalidate the current session.
     * @param model   Model to add attributes for the view.
     * @return the login view.
     */
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		System.out.println("In logout");
		session.invalidate();
		return "login";
	}

	

}
