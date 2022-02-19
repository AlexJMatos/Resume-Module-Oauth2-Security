package com.resume.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_DEFAULT)
@JsonIgnoreProperties(allowGetters = true, value = "id")
@Schema(description = "Data transfer object for technology information in GET, POST and PUT operations")
public class TechnologyDTO {
	
	private long id; 
	
	@NotBlank(message = "technology field cannot be null or empty")
	@Size(min = 1, max = 255, message = "technology field must be between 1 and 255 characters")
	private String technology;
	
	@NotBlank(message = "description field cannot be null or empty")
	@Size(min = 1, max = 255, message = "description field must be between 1 and 255 characters")
	private String description;
}