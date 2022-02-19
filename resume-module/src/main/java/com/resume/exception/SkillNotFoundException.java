package com.resume.exception;

public class SkillNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SkillNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkillNotFoundException(String message) {
		super(message);
	}

	public SkillNotFoundException(Throwable cause) {
		super(cause);
	}
}
