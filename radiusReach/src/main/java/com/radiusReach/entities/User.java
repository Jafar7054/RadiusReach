package com.bookYouServices.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a User entity that contains information about a user.
 */
@Entity
@Table(name="Userr")
@Data
@NoArgsConstructor
public class User {
	
	

	public User(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}


	/**
     * The unique identifier for the user.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
     * Name of the user.
     */
	private String userName;
	
	/**
     * Email of the user.
     */
	private String email;
	
	/**
     * Password of user.
     */
	private String password;	
	
	/**
     * List of services provided by User.
     */
	@OneToMany(mappedBy="vendor", fetch = FetchType.EAGER)
	@JsonIgnore
	List<Services> services;
	
	/**
     * The additional details of the user.
     */
	@OneToOne(mappedBy="user")
	@JsonIgnore
	UserAdditionalDetails userAdditionalDetails;
	
	
	/**
     * List of bookings made by user.
     */
	@OneToMany(mappedBy="user")
	@JsonIgnore
	List<Booking> booking;
	
	
	
	
}
