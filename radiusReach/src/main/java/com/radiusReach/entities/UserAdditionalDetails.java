package com.radiusReach.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents additional details of the user.
 */
@Entity
@Data
@NoArgsConstructor
public class UserAdditionalDetails {
	
	/**
	 * Constructs a new {@code UserAdditionalDetails} object with the specified service address, city, state, pincode, phone number and user.
	 * 
	 * @param address      the address of user.
	 * @param city         the city in which the user resides.
	 * @param state        the state in which the user resides.
	 * @param pincode      the pincode of the user.
	 * @param phonenNumber the phone number of the user.
	 * @param user         the service provider
	 *
	 */
	public UserAdditionalDetails(String address, String city, String state, int pincode, long phoneNumber, User user) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}
	
	/**
	 * Constructs a new {@code UserAdditionalDetails} object with the specified service address, city, state, pincode, and phone number.
	 * 
	 * @param address      the address of user.
	 * @param city         the city in which the user resides.
	 * @param state        the state in which the user resides.
	 * @param pincode      the pincode of the user.
	 * @param phonenNumber the phone number of the user.
	 *
	 */
	public UserAdditionalDetails(String address, String city, String state, int pincode, long phoneNumber) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.phoneNumber = phoneNumber;
	}
	/**
     * The unique identifier for the additional details.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	/**
     * The address of the user.
     */
	private String address;
	
	/**
     * The city of the user.
     */
	private String city;
	
	/**
     * The state of the user.
     */
	private String state;
	
	/**
     * The pincode of the user.
     */
	private int pincode;
	
	/**
     * The phone number of the user.
     */
	private long phoneNumber;

	/**
     * The User associated with the additional details.
     */
	@OneToOne()
	@JoinColumn(name = "user_id")
	@JsonIgnore
	User user;
	
}

