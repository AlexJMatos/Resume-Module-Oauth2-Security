package com.resume.service;

import java.util.Set;

import com.resume.dto.SkillDTO;

public interface SkillService {
	
	public Set<SkillDTO> findSkills(long employeeId);
	
	public SkillDTO findSkill(long employeeId, long skillId);
	
	public SkillDTO addSkill(long employeeId, SkillDTO skillDTO);
	
	public SkillDTO editSkill(long employeeId, long skillId, SkillDTO skillDTO);
}
