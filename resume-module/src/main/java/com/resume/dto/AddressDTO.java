package com.resume.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "Data transfer object for address basic information in GET, POST and PUT operations")
public class AddressDTO {

	@NotBlank(message = "address.street field cannot be null or empty")
	@Size(min = 1, max = 255, message = "street field must be between 1 and 255 characters")
	private String street;

	@NotBlank(message = "address.city field cannot be null or empty")
	@Size(min = 1, max = 255, message = "city field must be between 1 and 255 characters")
	private String city;

	@NotBlank(message = "address.state field cannot be null or empty")
	@Size(min = 1, max = 255, message = "state field must be between 1 and 255 characters")
	private String state;

	@NotBlank(message = "address.country field cannot be null or empty")
	@Size(min = 1, max = 255, message = "country field must be between 1 and 255 characters")
	private String country;

	@NotBlank(message = "address.zipCode field cannot be null or empty")
	@Digits(message = "zipCode must contain only 5 digits", fraction = 0, integer = 5)
	@Size(min = 5, max = 5, message = "zipCode must contain 5 digits")
	private String zipCode;
}
