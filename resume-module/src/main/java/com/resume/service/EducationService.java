package com.resume.service;

import java.util.Set;

import com.resume.dto.EducationDTO;

public interface EducationService {

	public Set<EducationDTO> findEducation(long employeeId);
	
	public EducationDTO findEducation(long employeeId, long educationId);
	
	public EducationDTO addEducationalDegree(long employeeId, EducationDTO educationDTO);
	
	public EducationDTO editEducationalDegree(long employeeId, long educationId, EducationDTO request);
}
