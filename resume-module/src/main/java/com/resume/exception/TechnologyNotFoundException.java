package com.resume.exception;

public class TechnologyNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TechnologyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TechnologyNotFoundException(String message) {
		super(message);
	}

	public TechnologyNotFoundException(Throwable cause) {
		super(cause);
	}
}
