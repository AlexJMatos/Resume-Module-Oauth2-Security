package com.resume.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dto.WorkExperienceDTO;
import com.resume.exception.ResumeNotFoundException;
import com.resume.exception.WorkExperienceNotFoundException;
import com.resume.model.Resume;
import com.resume.model.WorkExperience;
import com.resume.repository.ResumeRepository;
import com.resume.repository.WorkExperienceRepository;
import com.resume.service.WorkExperienceService;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

	private ResumeRepository resumeRepository;
	private WorkExperienceRepository workExperienceRepository;

	@Autowired
	public WorkExperienceServiceImpl(ResumeRepository resumeRepository,
			WorkExperienceRepository workExperienceRepository) {
		this.resumeRepository = resumeRepository;
		this.workExperienceRepository = workExperienceRepository;
	}

	@Override
	public Set<WorkExperienceDTO> findWorkExperience(long employeeId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<WorkExperience> workExperiences = workExperienceRepository.findByEmployeeId(employeeId);

			if (workExperiences.isEmpty()) {
				throw new WorkExperienceNotFoundException(
						"Resume for employee with ID " + employeeId + " doesn't have associated work experience");
			} else {
				Set<WorkExperienceDTO> workExperienceDTOs = new HashSet<WorkExperienceDTO>();

				workExperiences.forEach(work -> {
					WorkExperienceDTO workExperienceDTO = new WorkExperienceDTO();
					workExperienceDTO.setId(work.getId());
					workExperienceDTO.setPosition(work.getPosition());
					workExperienceDTO.setCompany(work.getCompany());
					workExperienceDTO.setDescription(work.getDescription());
					workExperienceDTO.setFromDate(work.getFromDate());
					workExperienceDTO.setUntilDate(work.getUntilDate());

					workExperienceDTOs.add(workExperienceDTO);
				});

				return workExperienceDTOs;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	@Transactional(rollbackOn = ResumeNotFoundException.class)
	public WorkExperienceDTO addWorkExperience(long employeeId, WorkExperienceDTO request) {
		/* Verify that the resume exists */
		if (resumeRepository.existsById(employeeId)) {

			/* Create work experience object */
			WorkExperience workExperience = new WorkExperience();
			workExperience.setPosition(request.getPosition());
			workExperience.setCompany(request.getCompany());
			workExperience.setDescription(request.getDescription());
			workExperience.setFromDate(request.getFromDate());
			workExperience.setUntilDate(request.getUntilDate());
			workExperience.setEmployeeId(employeeId);

			/* Verify the work experience doesn't already exists */
			Collection<WorkExperience> workExperiences = workExperienceRepository.findByEmployeeId(employeeId);

			boolean exists = workExperiences.stream().anyMatch(
					w -> w.getPosition().equals(request.getPosition()) && w.getCompany().equals(request.getCompany()));

			if (exists) {
				/* Retrieve the existing work experience */
				workExperience = workExperiences.stream().filter(w -> w.getPosition().equals(request.getPosition())
						&& w.getCompany().equals(request.getCompany())).findFirst().get();
			} else {
				/* Save the work experience */
				workExperience = workExperienceRepository.save(workExperience);
			}

			/* Create and return the DTO response */
			WorkExperienceDTO response = new WorkExperienceDTO();
			response.setId(workExperience.getId());
			response.setPosition(workExperience.getPosition());
			response.setCompany(workExperience.getCompany());
			response.setDescription(workExperience.getDescription());
			response.setFromDate(workExperience.getFromDate());
			response.setUntilDate(workExperience.getUntilDate());

			return response;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public WorkExperienceDTO editWorkExperience(long employeeId, long workId, WorkExperienceDTO request) {
		/* Verify the resume exists */
		if (resumeRepository.existsById(employeeId)) {
			/*
			 * Verify that the given resume has the id associated with the work experience
			 */
			Resume resume = resumeRepository.findById(employeeId).get();
			boolean degreeExists = resume.getWorkExperience().stream().anyMatch(w -> w.getId() == workId);

			if (degreeExists) {
				/* Create the work experience object */
				WorkExperience editedWorkExperience = workExperienceRepository.getById(workId);
				editedWorkExperience.setPosition(request.getPosition());
				editedWorkExperience.setCompany(request.getCompany());
				editedWorkExperience.setDescription(request.getDescription());
				editedWorkExperience.setFromDate(request.getFromDate());
				editedWorkExperience.setUntilDate(request.getUntilDate());
				editedWorkExperience.setEmployeeId(employeeId);

				/* Save the work experience */
				editedWorkExperience = workExperienceRepository.save(editedWorkExperience);

				/* Create the response DTO */
				WorkExperienceDTO response = new WorkExperienceDTO();
				response.setId(editedWorkExperience.getId());
				response.setPosition(editedWorkExperience.getPosition());
				response.setCompany(editedWorkExperience.getCompany());
				response.setDescription(editedWorkExperience.getDescription());
				response.setFromDate(editedWorkExperience.getFromDate());
				response.setUntilDate(editedWorkExperience.getUntilDate());

				return response;
			} else {
				throw new WorkExperienceNotFoundException("Work Experience with ID " + workId
						+ " not found in the resume for employee with ID " + employeeId);
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public WorkExperienceDTO findWorkExperience(long employeeId, long workId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<WorkExperience> workExperiences = workExperienceRepository.findByEmployeeId(employeeId);

			if (workExperiences.isEmpty()) {
				throw new WorkExperienceNotFoundException(
						"Resume for employee with ID " + employeeId + " doesn't have associated work experience");
			} else {
				WorkExperience work = workExperiences.stream().filter(w -> w.getId() == workId).findFirst()
						.orElse(null);

				if (work == null) {
					throw new WorkExperienceNotFoundException("Work Experience with ID " + workId
							+ " not found in the resume for employee with ID " + employeeId);
				}

				WorkExperienceDTO workExperienceDTO = new WorkExperienceDTO();
				workExperienceDTO.setId(work.getId());
				workExperienceDTO.setPosition(work.getPosition());
				workExperienceDTO.setCompany(work.getCompany());
				workExperienceDTO.setDescription(work.getDescription());
				workExperienceDTO.setFromDate(work.getFromDate());
				workExperienceDTO.setUntilDate(work.getUntilDate());

				return workExperienceDTO;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

}
