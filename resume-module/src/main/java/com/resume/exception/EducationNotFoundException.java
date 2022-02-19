package com.resume.exception;

public class EducationNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EducationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EducationNotFoundException(String message) {
		super(message);
	}

	public EducationNotFoundException(Throwable cause) {
		super(cause);
	}
}
