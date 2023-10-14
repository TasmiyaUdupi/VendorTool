package com.ot.VendorTool.exception;

public class IdNotFoundException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3506635001132353380L;
	
	private String message = "Id Is Not Present";

	public IdNotFoundException(String message) {
		this.message = message;
	}

	public IdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}