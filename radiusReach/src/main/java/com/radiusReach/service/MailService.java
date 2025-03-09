package com.bookYouServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for sending email notifications to users and vendors.
 */
@Service
@Slf4j
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	/**
     * Sends an email notification to a user about a booking request.
     * @param toAddress the recipient's email address.
     * @param serviceName the name of the booked service.
     * @param vendorContact the vendor's contact number.
     * @param userName the name of the user.
     */
	public void sendMailToUser(String toAddress, String serviceName, Long vendorContact, String userName)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("MS_8n0mo1@trial-3yxj6ljr7314do2r.mlsender.net");
		message.setTo(toAddress);
		message.setSubject("Booking Request Sent");
		message.setText("Dear"+userName+",\n Your booking request for "+serviceName+" service has been sent to the service provider.\nYou can wait for the vendor to approve it or contact the vendor to get it approved.\nContact Details:"+vendorContact+"\nThank You");
		mailSender.send(message);
		log.info("Mail sent to user:"+toAddress+"for booking request sent.");
	}
	
	/**
     * Sends an email notification to a vendor about a booking request.
     * @param toAddress the recipient's email address.
     * @param serviceName the name of the booked service.
     * @param userName the name of the user.
     */
	public void sendMailToVendor(String toAddress, String serviceName, String userName)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("MS_8n0mo1@trial-3yxj6ljr7314do2r.mlsender.net");
		message.setTo(toAddress);
		message.setSubject("Booking Request Received");
		message.setText("Dear"+userName+",\n You have received a booking request for "+serviceName+" service.\n.Approve the request on the website.\nThank You");
		mailSender.send(message);
		log.info("Mail sent to vendor:"+toAddress+"for booking request received.");
	}
	
	/**
     * Sends an email notification to a user about a booking request status change.
     * @param toAddress the recipient's email address.
     * @param serviceName the name of the booked service.
     * @param userName the name of the user.
     */
	public void sendMailOnBookingStatusChangeToUser(String toAddress, String serviceName, String userName)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("MS_8n0mo1@trial-3yxj6ljr7314do2r.mlsender.net");
		message.setTo(toAddress);
		message.setSubject("Booking Status changed");
		message.setText("Dear"+userName+",\n Status of your booking has changed for "+serviceName+" service.\n.Visit the website to check.\nThank You");
		mailSender.send(message);
		log.info("Mail sent to user"+toAddress+"for booking status update.");
	}
	
	/**
     * Sends an email notification to a vendor about a scheduled booking.
     * @param toAddress the recipient's email address.
     * @param serviceName the name of the booked service.
     * @param userName the name of the user.
     * @param date the date on which service is scheduled.
     * @param time the time at which service is scheduled.
     */
	public void sendMailOnBookingConfirmToVendor(String toAddress,String serviceName, String userName, String date, String time)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("MS_8n0mo1@trial-3yxj6ljr7314do2r.mlsender.net");
		message.setTo(toAddress);
		message.setSubject("Booking Status changed");
		message.setText("Dear"+userName+",\n You approved a booking for "+serviceName+" service on "+date+ "at"+time+".\nThank You");
		mailSender.send(message);
		log.info("Mail sent to "+toAddress+"for booking schedule.");
	}
	
	
}
