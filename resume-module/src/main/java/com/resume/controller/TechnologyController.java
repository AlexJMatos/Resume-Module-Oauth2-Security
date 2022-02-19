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
import com.resume.service.TechnologyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@RequestMapping("employees/{employeeId}/resume/technologies")
@Tag(name = "Technologies Controller", description = "Controller for operations related to technologies")
public class TechnologyController {

	private TechnologyService technologyService;

	@Autowired
	public TechnologyController(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	@GetMapping
	@Operation(summary = "View technologies associated with the employee's resume", responses = {
			@ApiResponse(description = "Technologies found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Technology not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> findTechnologies(@PathVariable("employeeId") @Min(1) long employeeId) {
		return ResponseEntity.ok(technologyService.findTechnologies(employeeId));
	}
	
	@GetMapping("/{technologyId}")
	@Operation(summary = "View a technology associated with the employee's resume", responses = {
			@ApiResponse(description = "Technology found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Technology not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "technologyId", required = true, description = "The ID of the technology")})
	public ResponseEntity<Object> findTechnology(@PathVariable("employeeId") @Min(1) long employeeId, @PathVariable("technologyId") @Min(1) long technologyId) {
		return ResponseEntity.ok(technologyService.findTechnology(employeeId, technologyId));
	}

	@PostMapping
	@Operation(summary = "Add technology to the employee's resume", responses = {
			@ApiResponse(description = "Technology created and added successfully", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Technology not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Incorrect type input. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> addTechnology(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid TechnologyDTO request) {
		return new ResponseEntity<>(technologyService.addTechnology(employeeId, request), HttpStatus.CREATED);
	}

	@PutMapping("/{technologyId}")
	@Operation(summary = "Edit technology from the employee's resume", responses = {
			@ApiResponse(description = "Technology edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Technology not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "technologyId", required = true, description = "The ID of the technology to be edited") })
	public ResponseEntity<Object> editTechnology(@PathVariable("employeeId") @Min(1) long employeeId,
			@PathVariable("technologyId") long technologyId, @RequestBody @Valid TechnologyDTO request) {
		return ResponseEntity.ok(technologyService.editTechnology(employeeId, technologyId, request));
	}
}
