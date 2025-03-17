package com.radiusReach.customException;


/**
 * Custom exception class to handle custom exceptions.
 */
public class CustomDatabaseException extends Exception{

	/**
	 * Constructs a new {@code CustomDatabaseException} object.
	 */
	public CustomDatabaseException()
	{
		super("An exception occured");
	}
	
	/**
	 * Constructs a new {@code CustomDatabaseException} object with the specified message.
	 * @param message  the message of the exception which can later be retrieved by the {@link Throwable#getMessage()} method.
	 */
	public CustomDatabaseException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructs a new {@code CustomDatabaseException} with the specified detail message and cause.
	 * 
	 * @param message the detail message, which can later be retrieved by the {@link Throwable#getMessage()} method.
	 * @param cause   the cause of the exception, which can be retrieved by the {@link Throwable#getCause()} method.
	 *                A {@code null} value is permitted, indicating that the cause is nonexistent or unknown.
	 */
	public CustomDatabaseException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
