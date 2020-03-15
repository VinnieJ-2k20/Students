package com.gmail.ptimofejev.exceptions;

public class GroupSizeExceededException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Group size exceeded, unable to add new Student.";
	}
}
