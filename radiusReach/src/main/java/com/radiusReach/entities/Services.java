package com.bookYouServices.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a service entity that contains information about a service.
 */
@Entity
@Data
@NoArgsConstructor
public class Services {

	/**
	 * Constructs a new {@code Services} object with the specified service name, price, description, address, city, state, pincode, phone number and vendor.
	 *
	 * @param serviceName   the name of the service
	 * @param price   the price of the service
	 * @param description   the description of the service.
	 * @param address   the address of service provider.
	 * @param city   the city in which service is provided.
	 * @param state  the state in which service is provided.
	 * @param pincode the pincode of the service provider.
	 * @param phonenNumber the phone number of the service provider.
	 * @param vendor the service provider
	 *
	 */
	public Services(String serviceName, double price, String description, String address, String city, String state,
			int pincode, long phoneNumber, User vendor) {
		super();
		this.serviceName = serviceName;
		this.price = price;
		this.description = description;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.phoneNumber = phoneNumber;
		this.vendor = vendor;
	}

	/**
     * The unique identifier for the service.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
     * The name of service provider
     */
	private String serviceName;
	/**
     * The price of the service
     */
	private double price;
	/**
     * The descritpion of the service.
     */
	private String description;
	/**
     * The address of the service provider..
     */
	private String address;
	/**
     * The city in which service is provided.
     */
	private String city;
	/**
     * The state in which service is provided.
     */
	private String state;
	/**
     * The pincode of the area where service is provided.
     */
	private int pincode;
	/**
     * The phone number of service provider..
     */
	private long phoneNumber;
	
	
	/**
     * The Vendor who is providing the service.
     */
	@ManyToOne()
	@JsonIgnore
	private User vendor;
	
	/**
     * List of bookings made for this service.
     */
	@OneToMany(mappedBy="service")
	@JsonIgnore
	List<Booking> booking;
	
	
}
