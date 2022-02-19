package com.resume.service;

import java.util.Set;

import com.resume.dto.TechnologyDTO;

public interface TechnologyService {
	
	public Set<TechnologyDTO> findTechnologies(long employeeId);
	
	public TechnologyDTO findTechnology(long employeeId, long technologyId);
	
	public TechnologyDTO addTechnology(long employeeId, TechnologyDTO technologyDTO);
	
	public TechnologyDTO editTechnology(long employeeId, long technologyId, TechnologyDTO request);
}
