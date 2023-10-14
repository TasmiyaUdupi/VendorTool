package com.ot.VendorTool.exception;

public class DuplicateDataEntryException extends RuntimeException {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5956840768820018027L;
	
	
	private String message = "This Data Is Already Exists";

	public DuplicateDataEntryException(String message) {
		this.message = message;
	}

	public DuplicateDataEntryException() {

	}

	@Override
	public String toString() {
		return message;
	}
}
