package com.resume.service;

import java.util.Set;

import com.resume.dto.WorkExperienceDTO;

public interface WorkExperienceService {

	public Set<WorkExperienceDTO> findWorkExperience(long employeeId);
	
	public WorkExperienceDTO findWorkExperience(long employeeId, long workId);
	
	public WorkExperienceDTO addWorkExperience(long employeeId, WorkExperienceDTO request);
	
	public WorkExperienceDTO editWorkExperience(long employeeId, long workId, WorkExperienceDTO request);
}
