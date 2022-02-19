package com.resume.exception;

public class ResumeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResumeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResumeNotFoundException(String message) {
		super(message);
	}

	public ResumeNotFoundException(Throwable cause) {
		super(cause);
	}
}
