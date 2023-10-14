package com.ot.VendorTool.exception;

public class PhoneNumberNotFoundException extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465062411790828924L;
	
	private String message = "Phone Number Is Not Present";

	public PhoneNumberNotFoundException(String message) {
		this.message = message;
	}

	public PhoneNumberNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}
