package com.microservice.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	
	// default constructor to print default message
	public ResourceNotFoundException() {
		super("Resource not found on server !!");
	}
	
	// parameterized constructor to print message set by user
	public ResourceNotFoundException(String message) {
		super("Resource not found on server !!");
	}

}
