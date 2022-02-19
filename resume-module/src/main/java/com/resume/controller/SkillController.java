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
import com.resume.dto.SkillDTO;
import com.resume.dto.TechnologyDTO;
import com.resume.service.SkillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@RequestMapping("employees/{employeeId}/resume/skills")
@Tag(name = "Skills Controller", description = "Controller for operations related to skills")
public class SkillController {

	private SkillService skillService;

	@Autowired
	public SkillController(SkillService skillService) {
		this.skillService = skillService;
	}

	@GetMapping
	@Operation(summary = "View skills associated with the employee's resume", responses = {
			@ApiResponse(description = "Skills found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SkillDTO.class))),
			@ApiResponse(description = "Skill not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> findSkills(@PathVariable("employeeId") @Min(1) long employeeId) {
		return ResponseEntity.ok(skillService.findSkills(employeeId));
	}
	
	@GetMapping("/{skillId}")
	@Operation(summary = "View a skill associated with the employee's resume", responses = {
			@ApiResponse(description = "Skill found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDTO.class))),
			@ApiResponse(description = "Skill not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "skillId", required = true, description = "The ID of the skill")})
	public ResponseEntity<Object> findSkill(@PathVariable("employeeId") @Min(1) long employeeId, @PathVariable("skillId") @Min(1) long skillId) {
		return ResponseEntity.ok(skillService.findSkill(employeeId, skillId));
	}

	@PostMapping
	@Operation(summary = "Add skill to the employee's resume", responses = {
			@ApiResponse(description = "Skill created and added successfully", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SkillDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Incorrect type input. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"), })
	public ResponseEntity<Object> addSkill(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid SkillDTO request) {
		return new ResponseEntity<>(skillService.addSkill(employeeId, request), HttpStatus.CREATED);
	}

	@PutMapping("/{skillId}")
	@Operation(summary = "Edit a skill from the employee's resume", responses = {
			@ApiResponse(description = "Skill edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SkillDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Skill not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee"),
					@Parameter(name = "skillId", required = true, description = "The ID of the skill to be edited") })
	public ResponseEntity<Object> editSkill(@PathVariable("employeeId") @Min(1) long employeeId,
			@PathVariable("skillId") @Valid long skillId, @RequestBody @Valid SkillDTO request) {
		return ResponseEntity.ok(skillService.editSkill(employeeId, skillId, request));
	}
}
