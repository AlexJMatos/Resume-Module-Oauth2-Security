package com.resume.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dto.TechnologyDTO;
import com.resume.exception.ResumeNotFoundException;
import com.resume.exception.TechnologyNotFoundException;
import com.resume.model.Resume;
import com.resume.model.Technology;
import com.resume.repository.ResumeRepository;
import com.resume.repository.TechnologyRepository;
import com.resume.service.TechnologyService;

@Service
public class TechnologyServiceImpl implements TechnologyService {

	private TechnologyRepository technologyRepository;
	private ResumeRepository resumeRepository;

	@Autowired
	public TechnologyServiceImpl(TechnologyRepository technologyRepository, ResumeRepository resumeRepository) {
		this.technologyRepository = technologyRepository;
		this.resumeRepository = resumeRepository;
	}

	@Override
	public Set<TechnologyDTO> findTechnologies(long employeeId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Technology> technologies = technologyRepository.findByEmployeeId(employeeId);

			if (technologies.isEmpty()) {
				throw new TechnologyNotFoundException(
						"Employee with ID " + employeeId + " doesn't have associated technologies in the resume");
			} else {
				Set<TechnologyDTO> technologyDTOs = new HashSet<TechnologyDTO>();

				technologies.forEach(technology -> {
					TechnologyDTO technologyDTO = new TechnologyDTO();
					technologyDTO.setId(technology.getId());
					technologyDTO.setTechnology(technology.getTechnology());
					technologyDTO.setDescription(technology.getDescription());

					technologyDTOs.add(technologyDTO);
				});

				return technologyDTOs;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	@Transactional(rollbackOn = ResumeNotFoundException.class)
	public TechnologyDTO addTechnology(long employeeId, TechnologyDTO technologyDTO) {
		/* Verify that the resume exists */
		if (resumeRepository.existsById(employeeId)) {

			/* Create technology object */
			Technology technology = new Technology();
			technology.setTechnology(technologyDTO.getTechnology());
			technology.setDescription(technologyDTO.getDescription());
			technology.setEmployeeId(employeeId);

			Collection<Technology> technologies = technologyRepository.findByEmployeeId(employeeId);
			boolean exists = technologies.stream()
					.anyMatch(t -> t.getTechnology().equals(technologyDTO.getTechnology()));

			if (exists) {
				/* Retrieve the existing technology */
				technology = technologies.stream().filter(t -> t.getTechnology().equals(technologyDTO.getTechnology()))
						.findFirst().get();
			} else {
				/* Save the technology */
				technology = technologyRepository.save(technology);
			}

			/* Create and return the DTO response */
			TechnologyDTO response = new TechnologyDTO();
			response.setId(technology.getId());
			response.setTechnology(technology.getTechnology());
			response.setDescription(technology.getDescription());

			return response;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public TechnologyDTO editTechnology(long employeeId, long technologyId, TechnologyDTO request) {
		/* Verify the resume exists */
		if (resumeRepository.existsById(employeeId)) {
			/* Verify that the given resume has the id associated with the technology */
			Resume resume = resumeRepository.findById(employeeId).get();
			boolean technologyExists = resume.getTechnologies().stream().anyMatch(t -> t.getId() == technologyId);

			if (technologyExists) {
				/* Create the technology object */
				Technology editedTechnology = technologyRepository.findById(technologyId).get();
				editedTechnology.setTechnology(request.getTechnology());
				editedTechnology.setDescription(request.getDescription());
				editedTechnology.setEmployeeId(employeeId);

				/* Save the technology */
				editedTechnology = technologyRepository.save(editedTechnology);

				/* Create the response DTO */
				TechnologyDTO response = new TechnologyDTO();
				response.setId(editedTechnology.getId());
				response.setTechnology(editedTechnology.getTechnology());
				response.setDescription(editedTechnology.getDescription());

				return response;
			} else {
				throw new TechnologyNotFoundException("Technology with ID " + technologyId
						+ " not found in the resume for employee with ID " + employeeId);
			}

		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public TechnologyDTO findTechnology(long employeeId, long technologyId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Technology> technologies = technologyRepository.findByEmployeeId(employeeId);

			if (technologies.isEmpty()) {
				throw new TechnologyNotFoundException(
						"Employee with ID " + employeeId + " doesn't have associated technologies in the resume");
			} else {
				Technology technology = technologies.stream().filter(t -> t.getId() == technologyId).findFirst()
						.orElse(null);
				if (technology == null) {
					throw new TechnologyNotFoundException("Technology with ID " + technologyId
							+ " not found in the resume for employee with ID " + employeeId);
				}
				TechnologyDTO technologyDTO = new TechnologyDTO();
				technologyDTO.setId(technology.getId());
				technologyDTO.setTechnology(technology.getTechnology());
				technologyDTO.setDescription(technology.getDescription());
				return technologyDTO;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}
}
