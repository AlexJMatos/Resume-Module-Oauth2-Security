package com.resume.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resume.dto.ErrorDTO;
import com.resume.dto.PagedResult;
import com.resume.dto.ResumeDTO;
import com.resume.dto.ResumePatchDTO;
import com.resume.enums.SearchBy;
import com.resume.service.ResumeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@RequestMapping("/employees")
@Slf4j
@Tag(name = "Resume Controller", description = "Controller for operations related to basic information (first name, last name, email, phone number and address)")
public class ResumeController {

	private ResumeService resumeService;

	@Autowired
	public ResumeController(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	@GetMapping("/resumes")
	@Operation(summary = "View all employees resumes information or search based on the request parameter", responses = {
			@ApiResponse(description = "Resumes found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class))),
			@ApiResponse(description = "Resumes not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "search", required = false, description = "search in the first names, last names, email, phone numbers, technologies, degrees, positions and companies of the resumes"),
					@Parameter(name = "filterBy", required = false, description = "specify where to filter in the resume, if left empty, search in everything"),
					@Parameter(name = "sort", required = false, description = "specify the parameter or field to sort by"),
					@Parameter(name = "pageable", required = false, description = "The request query for sorting, pageSize, pageNumber, offset and paged", content = @Content(schema = @Schema(implementation = Pageable.class, required = false))) })
	public ResponseEntity<Object> findAll(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "searchBy", required = false) SearchBy searchBy,
			@PageableDefault(sort = { "id" }) Pageable pageable) {
		log.info("search = {}, searchBy = {}", search, searchBy);
		PagedResult<ResumeDTO> resumesDTO = resumeService.findAll(search, searchBy, pageable);
		return new ResponseEntity<>(resumesDTO, HttpStatus.OK);
	}

	@GetMapping("{employeeId}/resume")
	@Operation(summary = "View employee's resume", responses = {
			@ApiResponse(description = "Resume found successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumeDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee") })
	public ResponseEntity<Object> findById(@PathVariable("employeeId") @Min(1) long employeeId) {
		ResumeDTO resumeDTO = resumeService.findById(employeeId);
		return ResponseEntity.ok(resumeDTO);
	}

	@PostMapping("/{employeeId}/resume")
	@Operation(summary = "Create an employee's resume", responses = {
			@ApiResponse(description = "Resume created successfully", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumeDTO.class))),
			@ApiResponse(description = "Missing JSON fields", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Incorrect type input. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee") })
	public ResponseEntity<Object> addResume(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid ResumeDTO request) {
		request.setId(employeeId);
		ResumeDTO resumeDTO = resumeService.save(request);
		return new ResponseEntity<>(resumeDTO, HttpStatus.CREATED);
	}

	@PutMapping("{employeeId}/resume")
	@Operation(summary = "Edit employee's resume basic information", responses = {
			@ApiResponse(description = "Resume edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumeDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Missing JSON fields. Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee") })
	public ResponseEntity<Object> updateWholeResume(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid ResumeDTO request) {
		ResumeDTO resumeDTO = resumeService.update(employeeId, request);
		return ResponseEntity.ok(resumeDTO);
	}

	@PatchMapping("{employeeId}/resume")
	@Operation(summary = "Edit partial employee's resume basic information", responses = {
			@ApiResponse(description = "Resume edited successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumeDTO.class))),
			@ApiResponse(description = "Resume not found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
			@ApiResponse(description = "Validation failed", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
					@Parameter(name = "employeeId", required = true, description = "The ID of the employee") })
	public ResponseEntity<Object> updatePartialResume(@PathVariable("employeeId") @Min(1) long employeeId,
			@RequestBody @Valid ResumePatchDTO request) {
		ResumeDTO resumeDTO = resumeService.patch(employeeId, request);
		return ResponseEntity.ok(resumeDTO);
	}
}
