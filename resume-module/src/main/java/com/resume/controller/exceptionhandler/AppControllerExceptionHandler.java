package com.resume.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.resume.dto.ErrorDTO;
import com.resume.errors.util.ErrorsHandlerUtil;
import com.resume.exception.EducationNotFoundException;
import com.resume.exception.ResumeNotFoundException;
import com.resume.exception.SkillNotFoundException;
import com.resume.exception.TechnologyNotFoundException;
import com.resume.exception.WorkExperienceNotFoundException;

@ControllerAdvice
public class AppControllerExceptionHandler {

	/* Exception handler if the element is not found */
	@ExceptionHandler(ResumeNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResumeNotFoundException(ResumeNotFoundException ex) {

		/* Create the error response */
		ErrorDTO errorResponse = new ErrorDTO();

		/* Setting the values for the JSON error response */
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/* Exception handler if the resume does not have technologies */
	@ExceptionHandler(TechnologyNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResumeNotFoundException(TechnologyNotFoundException ex) {

		/* Create the error response */
		ErrorDTO errorResponse = new ErrorDTO();

		/* Setting the values for the JSON error response */
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/* Exception handler if the resume does not have skills */
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResumeNotFoundException(SkillNotFoundException ex) {

		/* Create the error response */
		ErrorDTO errorResponse = new ErrorDTO();

		/* Setting the values for the JSON error response */
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/* Exception handler if the resume does not have education degrees */
	@ExceptionHandler(EducationNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResumeNotFoundException(EducationNotFoundException ex) {

		/* Create the error response */
		ErrorDTO errorResponse = new ErrorDTO();

		/* Setting the values for the JSON error response */
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/* Exception handler if the resume does not have work experience */
	@ExceptionHandler(WorkExperienceNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResumeNotFoundException(WorkExperienceNotFoundException ex) {

		/* Create the error response */
		ErrorDTO errorResponse = new ErrorDTO();

		/* Setting the values for the JSON error response */
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ErrorsHandlerUtil.getConciseErrorMessage(ex.getFieldErrors()));
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage("Incorrect path variable type: " + ex.getMostSpecificCause().getMessage().split("\"")[1]);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		String message = ex.getPathReference().split("\"")[1] + " must be a JSON Object";
		errorResponse.setMessage(message);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		String message = ex.getRootCause().getMessage();
		errorResponse.setMessage(message);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<Object> handleException(JsonParseException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		String message = ex.getLocalizedMessage();
		errorResponse.setMessage(message);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MismatchedInputException.class)
	public ResponseEntity<Object> handleException(MismatchedInputException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		String message = ex.getPathReference().split("\"")[1] + " must be a JSON Object";
		errorResponse.setMessage(message);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleException(IllegalArgumentException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage().split("on")[0].trim() + " on resume JSON Object");
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException ex) {

		// Create a general error response
		ErrorDTO errorResponse = new ErrorDTO();

		// Setting the values for the JSON error response
		errorResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		String message = ex.getLocalizedMessage();
		errorResponse.setMessage(message);
		errorResponse.setTimeStamp(System.currentTimeMillis());

		// Return the response
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
}
