package com.ot.VendorTool.exception;

public class EmailIdNotFoundException extends RuntimeException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6643754168044985450L;
	
	
	private String message = "Email-Id Is Not Present";

	public EmailIdNotFoundException(String message) {
		this.message = message;
	}

	public EmailIdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}