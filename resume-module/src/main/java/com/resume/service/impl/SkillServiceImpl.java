package com.resume.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dto.SkillDTO;
import com.resume.enums.SkillType;
import com.resume.exception.ResumeNotFoundException;
import com.resume.exception.SkillNotFoundException;
import com.resume.model.Resume;
import com.resume.model.Skill;
import com.resume.repository.ResumeRepository;
import com.resume.repository.SkillRepository;
import com.resume.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	private ResumeRepository resumeRepository;
	private SkillRepository skillRepository;

	@Autowired
	public SkillServiceImpl(SkillRepository skillRepository, ResumeRepository resumeRepository) {
		this.resumeRepository = resumeRepository;
		this.skillRepository = skillRepository;
	}

	@Override
	public Set<SkillDTO> findSkills(long employeeId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Skill> skills = skillRepository.findByEmployeeId(employeeId);

			if (skills.isEmpty()) {
				throw new SkillNotFoundException(
						"Employee with " + employeeId + " doesn't have associated skills in the resume");
			} else {
				Set<SkillDTO> skillDTOs = new HashSet<SkillDTO>();

				skills.forEach(skill -> {
					SkillDTO skillDTO = new SkillDTO();
					skillDTO.setId(skill.getId());
					skillDTO.setSkill(skill.getSkill());
					skillDTO.setType(skill.getType().toString());
					skillDTO.setDescription(skill.getDescription());

					skillDTOs.add(skillDTO);
				});

				return skillDTOs;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	@Transactional(rollbackOn = ResumeNotFoundException.class)
	public SkillDTO addSkill(long employeeId, SkillDTO skillDTO) {
		/* Verify that the resume exists */
		if (resumeRepository.existsById(employeeId)) {

			/* Create skill object */
			Skill skill = new Skill();
			skill.setSkill(skillDTO.getSkill());
			skill.setType(SkillType.valueOf(skillDTO.getType()));
			skill.setDescription(skillDTO.getDescription());
			skill.setEmployeeId(employeeId);

			/* Verify the skill doesn't already exists */
			Collection<Skill> skills = skillRepository.findByEmployeeId(employeeId);

			boolean exists = skills.stream().anyMatch(s -> s.getSkill().equals(skillDTO.getSkill())
					&& s.getType().equals(SkillType.valueOf(skillDTO.getType())));

			if (exists) {
				/* Retrieve the existing skill */
				skill = skills.stream().filter(s -> s.getSkill().equals(skillDTO.getSkill())
						&& s.getType().equals(SkillType.valueOf(skillDTO.getType()))).findFirst().get();
			} else {
				/* Save the skill */
				skill = skillRepository.save(skill);
			}

			/* Create and return the DTO response */
			SkillDTO response = new SkillDTO();
			response.setId(skill.getId());
			response.setSkill(skill.getSkill());
			response.setType(skill.getType().toString());
			response.setDescription(skill.getDescription());

			return response;
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public SkillDTO editSkill(long employeeId, long skillId, SkillDTO skillDTO) {
		/* Verify the resume exists */
		if (resumeRepository.existsById(employeeId)) {
			/* Verify that the given resume has the id associated with the skill */
			Resume resume = resumeRepository.findById(employeeId).get();
			boolean skillExists = resume.getSkills().stream().anyMatch(s -> s.getId() == skillId);

			if (skillExists) {
				/* Create the skill object */
				Skill editedSkill = skillRepository.getById(skillId);
				editedSkill.setSkill(skillDTO.getSkill());
				editedSkill.setType(SkillType.valueOf(skillDTO.getType()));
				editedSkill.setDescription(skillDTO.getDescription());
				editedSkill.setEmployeeId(employeeId);

				/* Save the skill */
				editedSkill = skillRepository.save(editedSkill);

				/* Create the response DTO */
				SkillDTO response = new SkillDTO();
				response.setId(editedSkill.getId());
				response.setSkill(editedSkill.getSkill());
				response.setType(editedSkill.getType().toString());
				response.setDescription(editedSkill.getDescription());

				return response;
			} else {
				throw new SkillNotFoundException(
						"Skill with ID " + skillId + " not found in the resume for employee with ID " + employeeId);
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}

	@Override
	public SkillDTO findSkill(long employeeId, long skillId) {
		if (resumeRepository.existsById(employeeId)) {
			Collection<Skill> skills = skillRepository.findByEmployeeId(employeeId);

			if (skills.isEmpty()) {
				throw new SkillNotFoundException(
						"Employee with " + employeeId + " doesn't have associated skills in the resume");
			} else {
				Skill skill = skills.stream().filter(s -> s.getId() == skillId).findFirst().orElse(null);

				if (skill == null) {
					throw new SkillNotFoundException(
							"Skill with ID " + skillId + " not found in the resume for employee with ID " + employeeId);
				}
				
				SkillDTO skillDTO = new SkillDTO();
				skillDTO.setId(skill.getId());
				skillDTO.setSkill(skill.getSkill());
				skillDTO.setType(skill.getType().toString());
				skillDTO.setDescription(skill.getDescription());

				return skillDTO;
			}
		} else {
			throw new ResumeNotFoundException("Resume for employee with ID " + employeeId + " not found");
		}
	}
}
