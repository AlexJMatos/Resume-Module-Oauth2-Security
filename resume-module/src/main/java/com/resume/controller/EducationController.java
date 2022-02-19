package com.resume.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resume.dto.EducationDTO;
import com.resume.dto.ErrorDTO;
import com.resume.dto.TechnologyDTO;
import com.resume.service.EducationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@RequestMapping("/employees/{employeeId}/resume/education")
@Tag(name = "Education Controller", description = "Controller for operations related to education")
public class EducationController {

	private EducationService educationService;

	@Autowired
	public EducationController(EducationService educationService) {
		this.educationService = educationService;
	}

	@GetMapping
	@Operation(summary = "View educational background associated with the employee's resume", responses = {
			@ApiResponse(description = "Educational background found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EducationDTO.class))),
			@ApiResponse(description = "Educational background not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> findEducation(@PathVariable("employeeId") @Min(1) long employeeId) {
		return ResponseEntity.ok(educationService.findEducation(employeeId));
	}
	
	@GetMapping("/{educationId}")
	@Operation(summary = "View an educational degree associated with the employee's resume", responses = {
			@ApiResponse(description = "Educational degree found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Educational degree not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "educationId", required = true, description = "The ID of the degree")})
	public ResponseEntity<Object> findSkill(@PathVariable("employeeId") @Min(1) long employeeId, @PathVariable("educationId") @Min(1) long educationId) {
		return ResponseEntity.ok(educationService.findEducation(employeeId, educationId));
	}

	@PostMapping
	@Operation(summary = "Add educational degree to the employee's resume", responses = {
			@ApiResponse(description = "Educational degree created and added successfully", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EducationDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Incorrect type input. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> addEducationalDegree(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid EducationDTO request) {
		return new ResponseEntity<>(educationService.addEducationalDegree(employeeId, request), HttpStatus.CREATED);
	}

	@PutMapping("/{educationId}")
	@Operation(summary = "Edit educational degree from the employee's resume", responses = {
			@ApiResponse(description = "Educational degree edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EducationDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Degree not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "educationId", required = true, description = "The ID of the degree to be edited") })
	public ResponseEntity<Object> editEducationalDegree(@PathVariable("employeeId") @Min(1) long employeeId,
			@PathVariable("educationId") @Min(1) long educationId, @RequestBody @Valid EducationDTO request) {
		return ResponseEntity.ok(educationService.editEducationalDegree(employeeId, educationId, request));
	}
}
