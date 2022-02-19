package com.resume.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.dto.AddressDTO;
import com.resume.dto.EducationDTO;
import com.resume.dto.PagedResult;
import com.resume.dto.ResumeDTO;
import com.resume.dto.ResumePatchDTO;
import com.resume.dto.SkillDTO;
import com.resume.dto.TechnologyDTO;
import com.resume.dto.WorkExperienceDTO;
import com.resume.enums.SearchBy;
import com.resume.exception.ResumeNotFoundException;
import com.resume.model.Address;
import com.resume.model.Education_;
import com.resume.model.Resume;
import com.resume.model.Resume_;
import com.resume.model.Technology_;
import com.resume.model.WorkExperience_;
import com.resume.repository.ResumeRepository;
import com.resume.service.ResumeService;
import com.resume.specification.ResumeSpecification;
import com.resume.specification.SearchCriteria;

@Service
public class ResumeServiceImpl implements ResumeService {

	private ResumeRepository resumeRepository;

	@Autowired
	public ResumeServiceImpl(ResumeRepository resumeRepository, Validator validator) {
		this.resumeRepository = resumeRepository;
	}

	@Override
	public PagedResult<ResumeDTO> findAll(String search, SearchBy searchBy, Pageable pageable) {
		/* Create an specification for resumes */
		Specification<Resume> resumeSpecification = Specification.where(null);

		/*
		 * Search the parameter in the firstName, lastName, email, phoneNumber,
		 * technology name, degree name, position and company
		 */
		if (search != null) {
			/* Searching by first name */
			Specification<Resume> firstNameSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Resume_.FIRST_NAME, "~", search)));

			/* Searching by last name */
			Specification<Resume> lastNameSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Resume_.LAST_NAME, "~", search)));

			/* Searching by email */
			Specification<Resume> emailSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Resume_.EMAIL, "~", search)));

			/* Searching by phone number */
			Specification<Resume> phoneNumberSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Resume_.PHONE_NUMBER, "~", search)));

			/* Searching by technology */
			Specification<Resume> technologySpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Technology_.TECHNOLOGY, "@", search)));

			/* Searching by degree name */
			Specification<Resume> degreeNameSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(Education_.DEGREE, "?", search)));

			/* Searching by position */
			Specification<Resume> workPositionSpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(WorkExperience_.POSITION, "/", search)));

			/* Searching by company */
			Specification<Resume> companySpecification = Specification
					.where(new ResumeSpecification(new SearchCriteria(WorkExperience_.COMPANY, "/", search)));

			if (searchBy == SearchBy.firstName) {
				resumeSpecification = resumeSpecification.or(firstNameSpecification);
			} else if (searchBy == SearchBy.lastName) {
				resumeSpecification = resumeSpecification.or(lastNameSpecification);
			} else if (searchBy == SearchBy.email) {
				resumeSpecification = resumeSpecification.or(emailSpecification);
			} else if (searchBy == SearchBy.phoneNumber) {
				resumeSpecification = resumeSpecification.or(phoneNumberSpecification);
			} else if (searchBy == SearchBy.technology) {
				resumeSpecification = resumeSpecification.or(technologySpecification);
			} else if (searchBy == SearchBy.degree) {
				resumeSpecification = resumeSpecification.or(degreeNameSpecification);
			} else if (searchBy == SearchBy.position) {
				resumeSpecification = resumeSpecification.or(workPositionSpecification);
			} else if (searchBy == SearchBy.company) {
				resumeSpecification = resumeSpecification.or(companySpecification);
			} else {
				resumeSpecification = resumeSpecification.or(firstNameSpecification).or(lastNameSpecification)
						.or(emailSpecification).or(phoneNumberSpecification).or(technologySpecification)
						.or(degreeNameSpecification).or(workPositionSpecification).or(companySpecification);
			}
		}

		/* Find all resumes with the given specification */
		Page<Resume> pageResumes = resumeRepository.findAll(resumeSpecification, pageable);

		/* Throw an exception in case no resume was found */
		if (pageResumes.isEmpty()) {
			throw new ResumeNotFoundException("No resume found");
		}

		/* Create a list for saving the resumes data access objects */
		List<ResumeDTO> resumes = new ArrayList<>();

		/* Convert to a data access object */
		pageResumes.getContent().forEach(resume -> {
			ResumeDTO resumeDTO = setResumeDTO(resume);

			/* Adding resume dto to the resumes list */
			resumes.add(resumeDTO);
		});

		/* Create the PagedResult DTO for the response */
		PagedResult<ResumeDTO> pagedResumesResult = new PagedResult<>();
		pagedResumesResult.setPageNumber(pageResumes.getNumber());
		pagedResumesResult.setPageSize(pageResumes.getSize());
		pagedResumesResult.setTotalElements(pageResumes.getTotalElements());
		pagedResumesResult.setTotalPages(pageResumes.getTotalPages());
		pagedResumesResult.setData(resumes.stream().distinct().collect(Collectors.toList()));

		return pagedResumesResult;
	}

	@Override
	public ResumeDTO findById(long employeeId) {
		/* Find the resume */
		Optional<Resume> result = resumeRepository.findById(employeeId);
		Resume resume = null;
		ResumeDTO resumeDTO = null;

		/* If the resume was found */
		if (result.isPresent()) {
			/* Get the resume inside optional */
			resume = result.get();

			/* Set the resume DTO */
			resumeDTO = setResumeDTO(resume);

			return resumeDTO;
		} else {
			/* Throw not found exception if the result is not present */
			throw new ResumeNotFoundException("No resume found for the given employee ID");
		}
	}

	@Override
	public ResumeDTO save(ResumeDTO request) {
		/* Set values for new resume core information */
		Resume resume = setResumePost(request);

		/* Save the resume information */
		resume = resumeRepository.save(resume);

		/* Return the resume DTO as a response */
		ResumeDTO resumeDTO = setResumeDTO(resume);
		return resumeDTO;
	}

	@Override
	public ResumeDTO update(long employeeId, ResumeDTO request) {
		/* Verify that the resume with the given ID exists */
		if (resumeRepository.existsById(employeeId)) {
			/* Setting the resume data */
			request.setId(employeeId);
			Resume resume = setResumePut(request);

			/* Saving the resume data */
			resume = resumeRepository.save(resume);

			/* Creating the DTO response */
			ResumeDTO resumeDTO = setResumeDTO(resume);

			return resumeDTO;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public ResumeDTO patch(long employeeId, ResumePatchDTO request) {
		Optional<Resume> resume = resumeRepository.findById(employeeId);

		ObjectMapper mapper = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> fields = mapper.convertValue(request, Map.class);
		fields.values().removeIf(v -> v == null);

		if (resume.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Resume.class, key);
				field.setAccessible(true);

				if (value instanceof LinkedHashMap) {

					@SuppressWarnings("unchecked")
					LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) value;
					object.values().removeIf(v -> v == null);

					object.forEach((objectKey, objectValue) -> {
						Field objectField = ReflectionUtils.findRequiredField(Address.class, objectKey);
						ReflectionUtils.setField(objectField, resume.get().getAddress(), objectValue);
					});
				} else {
					ReflectionUtils.setField(field, resume.get(), value);
				}
			});
			/* Setting the field values for resume */
			Resume patchResume = resumeRepository.save(resume.get());
			ResumeDTO resumeDTO = setResumeDTO(patchResume);
			return resumeDTO;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	private Resume setResumePost(ResumeDTO resumeRequest) {
		Resume resume = new Resume();
		resume.setId(resumeRequest.getId());
		resume.setFirstName(resumeRequest.getFirstName());
		resume.setLastName(resumeRequest.getLastName());
		resume.setEmail(resumeRequest.getEmail());
		resume.setPhoneNumber(resumeRequest.getPhoneNumber());

		Address address = new Address();
		if (resumeRepository.existsById(resume.getId())) {
			address.setId(resumeRepository.findById(resumeRequest.getId()).get().getAddress().getId());
		}
		address.setStreet(resumeRequest.getAddress().getStreet());
		address.setCity(resumeRequest.getAddress().getCity());
		address.setState(resumeRequest.getAddress().getState());
		address.setCountry(resumeRequest.getAddress().getCountry());
		address.setZipCode(resumeRequest.getAddress().getZipCode());
		resume.setAddress(address);

		return resume;
	}

	private Resume setResumePut(ResumeDTO resumeRequest) {
		Resume resume = new Resume();
		resume.setId(resumeRequest.getId());
		resume.setFirstName(resumeRequest.getFirstName());
		resume.setLastName(resumeRequest.getLastName());
		resume.setEmail(resumeRequest.getEmail());
		resume.setPhoneNumber(resumeRequest.getPhoneNumber());

		Address address = new Address();
		if (resumeRepository.existsById(resume.getId())) {
			address.setId(resumeRepository.findById(resumeRequest.getId()).get().getAddress().getId());
		}
		address.setStreet(resumeRequest.getAddress().getStreet());
		address.setCity(resumeRequest.getAddress().getCity());
		address.setState(resumeRequest.getAddress().getState());
		address.setCountry(resumeRequest.getAddress().getCountry());
		address.setZipCode(resumeRequest.getAddress().getZipCode());
		resume.setAddress(address);

		/* Setting technologies, skill, education and work experience */
		Optional<Resume> resumeInfo = resumeRepository.findById(resumeRequest.getId());

		if (resumeInfo.isPresent()) {
			resume.setTechnologies(resumeInfo.get().getTechnologies());
			resume.setSkills(resumeInfo.get().getSkills());
			resume.setEducation(resumeInfo.get().getEducation());
			resume.setWorkExperience(resumeInfo.get().getWorkExperience());
		}

		return resume;
	}

	private ResumeDTO setResumeDTO(Resume resume) {
		ResumeDTO resumeDTO = new ResumeDTO();

		/* Setting id, firstName, lastName, email and phone number */
		resumeDTO.setId(resume.getId());
		resumeDTO.setFirstName(resume.getFirstName());
		resumeDTO.setLastName(resume.getLastName());
		resumeDTO.setEmail(resume.getEmail());
		resumeDTO.setPhoneNumber(resume.getPhoneNumber());

		/* Setting address object */
		AddressDTO addressDTO = new AddressDTO();

		addressDTO.setStreet(resume.getAddress().getStreet());
		addressDTO.setCity(resume.getAddress().getCity());
		addressDTO.setState(resume.getAddress().getState());
		addressDTO.setCountry(resume.getAddress().getCountry());
		addressDTO.setZipCode(resume.getAddress().getZipCode());

		resumeDTO.setAddress(addressDTO);

		/* Setting technologies objects */
		Set<TechnologyDTO> technologyDTOs = new HashSet<>();

		resume.getTechnologies().forEach(technology -> {
			TechnologyDTO technologyDTO = new TechnologyDTO();

			technologyDTO.setTechnology(technology.getTechnology());
			technologyDTO.setDescription(technology.getDescription());

			technologyDTOs.add(technologyDTO);
		});

		resumeDTO.setTechnologies(technologyDTOs);

		/* Setting skills objects */
		Set<SkillDTO> skillDTOs = new HashSet<>();

		resume.getSkills().forEach(skill -> {
			SkillDTO skillDTO = new SkillDTO();

			skillDTO.setSkill(skill.getSkill());
			skillDTO.setType(skill.getType().toString());
			skillDTO.setDescription(skill.getDescription());

			skillDTOs.add(skillDTO);
		});

		resumeDTO.setSkills(skillDTOs);

		/* Setting education objects */
		Set<EducationDTO> educationDTOs = new HashSet<>();

		resume.getEducation().forEach(education -> {
			EducationDTO educationDTO = new EducationDTO();

			educationDTO.setDegree(education.getDegree());
			educationDTO.setType(education.getType().toString());
			educationDTO.setFromDate(education.getFromDate());
			educationDTO.setUntilDate(education.getUntilDate());

			educationDTOs.add(educationDTO);
		});

		resumeDTO.setEducation(educationDTOs);

		/* Setting work experience objects */
		Set<WorkExperienceDTO> workExperienceDTOs = new HashSet<>();

		resume.getWorkExperience().forEach(work -> {
			WorkExperienceDTO workExperienceDTO = new WorkExperienceDTO();

			workExperienceDTO.setPosition(work.getPosition());
			workExperienceDTO.setCompany(work.getCompany());
			workExperienceDTO.setDescription(work.getDescription());
			workExperienceDTO.setFromDate(work.getFromDate());
			workExperienceDTO.setUntilDate(work.getUntilDate());

			workExperienceDTOs.add(workExperienceDTO);
		});

		resumeDTO.setWorkExperience(workExperienceDTOs);

		return resumeDTO;
	}
}
