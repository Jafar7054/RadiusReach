package com.radiusReach.entities;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a Booking entity that contains information about a service booking made by a user.
 */
@Entity
@Data
@NoArgsConstructor
public class Booking {
	
	/**
	 * Constructs a new {@code Booking} object with the specified date, time, job status, request status, user and service.
	 *
	 * @param date   the date of booking
	 * @param time   the time of booking
	 * @param jobstatus   the job status of booking
	 * @param requestStatus   the request status of booking
	 * @param user   the user who booked the service
	 * @param service the service booked by user
	 *
	 */
	public Booking(String date, String time, String jobStatus, String requestStatus, User user, Services service) {
		super();
		this.date = date;
		this.time = time;
		this.jobStatus = jobStatus;
		this.requestStatus = requestStatus;
		this.user = user;
		this.service = service;
	}

	/**
     * The unique identifier for the booking.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
     * The date of the booking.
     */
	private String date;
	/**
     * The time of the booking.
     */
	private String time;
	/**
     * The current job status of the booking.
     */
	private String jobStatus;
	/**
     * The request status of the booking.
     */
	private String requestStatus;
	
	/**
     * The user who made the booking.
     */
	@ManyToOne
	@JsonIgnore
	private User user;
	
	/**
     * The service associated with the booking.
     */
	@ManyToOne
	@JsonIgnore
	private Services service;
	
}