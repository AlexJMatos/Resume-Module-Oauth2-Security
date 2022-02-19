package com.resume.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/* Define the data access object: field that we want to show in the JSON request */
@Data
@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, allowSetters = false, value = { "id", "technologies", "skills", "education",
		"workExperience" })
@Schema(description = "Data transfer object for resume information in GET, POST and PUT operations")
@JsonPropertyOrder({"employeeId", "firstName", "lastName", "email", "phoneNumber", "address", "technologies", "skills", "education", "workExperience"})
public class ResumeDTO {

	@JsonProperty("employeeId")
	private long id;

	@NotBlank(message = "firstName field cannot be null or empty")
	@Size(min = 1, max = 255, message = "firstName field must be between 1 and 255 characters")
	private String firstName;

	@NotBlank(message = "lastName field cannot be null or empty")
	@Size(min = 1, max = 255, message = "lastName field must be between 1 and 255 characters")
	private String lastName;

	@NotBlank(message = "email field cannot be null or empty")
	@Email(message = "invalid format for field email")
	@Size(min = 1, max = 255, message = "email field must be between 1 and 255 characters")
	private String email;

	@NotBlank(message = "phoneNumber field cannot be null or empty")
	@Pattern(regexp = "^(\\d{3}[-]?){2}\\d{4}$", message = "phoneNumber must have the format ddd-ddd-dddd")
	@Size(min = 1, max = 255, message = "phoneNumber field must be between 1 and 255 characters")
	private String phoneNumber;

	@NotNull(message = "address field object cannot be null")
	@Valid
	private AddressDTO address;

	private Set<TechnologyDTO> technologies = new HashSet<>();

	private Set<SkillDTO> skills = new HashSet<>();

	private Set<EducationDTO> education = new HashSet<>();

	private Set<WorkExperienceDTO> workExperience = new HashSet<>();
}
