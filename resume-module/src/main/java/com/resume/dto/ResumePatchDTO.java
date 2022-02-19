package com.resume.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/* DTO for PATCH request 
 * Ask for direct information related to the resume */
@Data
@Schema(description = "Data transfer object for resume information in PATCH operation")
public class ResumePatchDTO {
	
	@Size(min = 1, max = 255, message = "firstName field must be between 1 and 255 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "firstName must contain only letters")
	private String firstName;
	
	@Size(min = 1, max = 255, message = "lastName field must be between 1 and 255 characters")
	@Pattern(regexp = "[a-zA-Z]+\\.?", message = "lastName must contain only letters")
	private String lastName;
	
	@Email(message = "invalid format for field email")
	private String email;
	
	@Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd")
	private String phoneNumber;
	
	@Valid
	private AddressPatchDTO address;
}

