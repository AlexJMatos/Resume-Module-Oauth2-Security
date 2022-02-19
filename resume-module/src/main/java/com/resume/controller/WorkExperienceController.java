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

import com.resume.dto.ErrorDTO;
import com.resume.dto.TechnologyDTO;
import com.resume.dto.WorkExperienceDTO;
import com.resume.service.WorkExperienceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@RequestMapping("employees/{employeeId}/resume/work-experience")
@Tag(name = "Work Experience Controller", description = "Controller for operations related to work experience")
public class WorkExperienceController {

	private WorkExperienceService workExperienceService;

	@Autowired
	public WorkExperienceController(WorkExperienceService workExperienceService) {
		this.workExperienceService = workExperienceService;
	}

	@GetMapping
	@Operation(summary = "View work experience associated with the employee's resume", responses = {
			@ApiResponse(description = "Work experience found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkExperienceDTO.class))),
			@ApiResponse(description = "Work experience not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> findWorkExperience(@PathVariable("employeeId") @Min(1) long employeeId) {
		return ResponseEntity.ok(workExperienceService.findWorkExperience(employeeId));
	}
	
	@GetMapping("/{workId}")
	@Operation(summary = "View a job experience associated with the employee's resume", responses = {
			@ApiResponse(description = "Work experience found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Work experience not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "workId", required = true, description = "The ID of the job")})
	public ResponseEntity<Object> findTechnology(@PathVariable("employeeId") @Min(1) long employeeId, @PathVariable("workId") @Min(1) long workId) {
		return ResponseEntity.ok(workExperienceService.findWorkExperience(employeeId, workId));
	}

	@PostMapping
	@Operation(summary = "Add work experience to the employee's resume", responses = {
			@ApiResponse(description = "Work experience created and added successfully", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkExperienceDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Incorrect type input. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> addWorkExperience(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid WorkExperienceDTO request) {
		return new ResponseEntity<>(workExperienceService.addWorkExperience(employeeId, request), HttpStatus.CREATED);
	}

	@PutMapping("/{workId}")
	@Operation(summary = "Edit work experience from the employee's resume", responses = {
			@ApiResponse(description = "Work experience edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkExperienceDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Work experience not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "workId", required = true, description = "The ID of the work experience to be edited") })
	public ResponseEntity<Object> editWorkExperience(@PathVariable("employeeId") @Min(1) long employeeId,
			@PathVariable("workId") @Min(1) long workId, @RequestBody @Valid WorkExperienceDTO request) {
		return ResponseEntity.ok(workExperienceService.editWorkExperience(employeeId, workId, request));
	}
}
