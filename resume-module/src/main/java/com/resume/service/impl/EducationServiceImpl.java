package com.resume.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dto.EducationDTO;
import com.resume.enums.DegreeType;
import com.resume.exception.EducationNotFoundException;
import com.resume.exception.ResumeNotFoundException;
import com.resume.model.Education;
import com.resume.model.Resume;
import com.resume.repository.EducationRepository;
import com.resume.repository.ResumeRepository;
import com.resume.service.EducationService;

@Service
public class EducationServiceImpl implements EducationService {

	private ResumeRepository resumeRepository;
	private EducationRepository educationRepository;

	@Autowired
	public EducationServiceImpl(ResumeRepository resumeRepository, EducationRepository educationRepository) {
		this.resumeRepository = resumeRepository;
		this.educationRepository = educationRepository;
	}

	@Override
	public Set<EducationDTO> findEducation(long employeeId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Education> degrees = educationRepository.findByEmployeeId(employeeId);

			if (degrees.isEmpty()) {
				throw new EducationNotFoundException(
						"Resume for employee with " + employeeId + " doesn't have associated degrees or education");
			} else {
				Set<EducationDTO> educationDTOs = new HashSet<EducationDTO>();

				degrees.forEach(degree -> {
					EducationDTO educationDTO = new EducationDTO();
					educationDTO.setId(degree.getId());
					educationDTO.setDegree(degree.getDegree());
					educationDTO.setType(degree.getType().toString());
					educationDTO.setFromDate(degree.getFromDate());
					educationDTO.setUntilDate(degree.getUntilDate());

					educationDTOs.add(educationDTO);
				});

				return educationDTOs;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	@Transactional(rollbackOn = ResumeNotFoundException.class)
	public EducationDTO addEducationalDegree(long employeeId, EducationDTO educationDTO) {
		/* Verify that the resume exists */
		if (resumeRepository.existsById(employeeId)) {

			/* Create education degree object */
			Education degree = new Education();
			degree.setDegree(educationDTO.getDegree());
			degree.setType(DegreeType.valueOf(educationDTO.getType()));
			degree.setFromDate(educationDTO.getFromDate());
			degree.setUntilDate(educationDTO.getUntilDate());
			degree.setEmployeeId(employeeId);

			/* Verify the educational degree doesn't exists already */
			Collection<Education> degrees = educationRepository.findByEmployeeId(employeeId);

			boolean exists = degrees.stream().anyMatch(d -> d.getDegree().equals(educationDTO.getDegree())
					&& d.getType().equals(DegreeType.valueOf(educationDTO.getType())));

			if (exists) {
				/* Retrieve the existing degree */
				degree = degrees.stream().filter(d -> d.getDegree().equals(educationDTO.getDegree())
						&& d.getType().equals(DegreeType.valueOf(educationDTO.getType()))).findFirst().get();
			} else {
				/* Save the educational degree */
				degree = educationRepository.save(degree);
			}

			/* Create and return the DTO response */
			EducationDTO response = new EducationDTO();
			response.setId(degree.getId());
			response.setDegree(degree.getDegree());
			response.setType(degree.getType().toString());
			response.setFromDate(degree.getFromDate());
			response.setUntilDate(degree.getUntilDate());

			return response;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public EducationDTO editEducationalDegree(long employeeId, long educationId, EducationDTO request) {
		/* Verify the resume exists */
		if (resumeRepository.existsById(employeeId)) {
			/*
			 * Verify that the given resume has the id associated with the educational
			 * degree
			 */
			Resume resume = resumeRepository.findById(employeeId).get();
			boolean degreeExists = resume.getEducation().stream().anyMatch(e -> e.getId() == educationId);

			if (degreeExists) {
				/* Create the degree object */
				Education editedDegree = educationRepository.getById(educationId);
				editedDegree.setDegree(request.getDegree());
				editedDegree.setType(DegreeType.valueOf(request.getType()));
				editedDegree.setFromDate(request.getFromDate());
				editedDegree.setUntilDate(request.getUntilDate());
				editedDegree.setEmployeeId(employeeId);

				/* Save the degree */
				editedDegree = educationRepository.save(editedDegree);

				/* Create the response DTO */
				EducationDTO response = new EducationDTO();
				response.setId(editedDegree.getId());
				response.setDegree(editedDegree.getDegree());
				response.setType(editedDegree.getType().toString());
				response.setFromDate(editedDegree.getFromDate());
				response.setUntilDate(editedDegree.getUntilDate());

				return response;
			} else {
				throw new EducationNotFoundException("Education with ID " + educationId
						+ " not found in the resume for employee with ID " + employeeId);
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public EducationDTO findEducation(long employeeId, long educationId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Education> degrees = educationRepository.findByEmployeeId(employeeId);

			if (degrees.isEmpty()) {
				throw new EducationNotFoundException(
						"Resume for employee with " + employeeId + " doesn't have associated degrees or education");
			} else {
				Education degree = degrees.stream().filter(e -> e.getId() == educationId).findFirst().orElse(null);

				if (degree == null) {
					throw new EducationNotFoundException("Degree with ID " + educationId
							+ " not found in the resume for employee with ID " + employeeId);
				}

				EducationDTO educationDTO = new EducationDTO();
				educationDTO.setId(degree.getId());
				educationDTO.setDegree(degree.getDegree());
				educationDTO.setType(degree.getType().toString());
				educationDTO.setFromDate(degree.getFromDate());
				educationDTO.setUntilDate(degree.getUntilDate());

				return educationDTO;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}
}
