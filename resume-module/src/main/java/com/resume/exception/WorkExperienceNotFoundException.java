package com.resume.exception;

public class WorkExperienceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public WorkExperienceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkExperienceNotFoundException(String message) {
		super(message);
	}

	public WorkExperienceNotFoundException(Throwable cause) {
		super(cause);
	}
}
