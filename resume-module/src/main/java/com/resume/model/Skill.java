package com.resume.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.resume.enums.SkillType;
import lombok.Data;

@Entity
@Data
@Table(name = "skills")
public class Skill {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "skill")
	@NotBlank(message = "skill must not be null or empty")
	private String skill;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private SkillType type;
	
	@Column(name = "description")
	@NotBlank(message = "description must not be null or empty")
	private String description;
	
	@Column(name = "skill_employee_id")
	@NotNull(message = "the employee id cannot be null")
	private long employeeId;
}