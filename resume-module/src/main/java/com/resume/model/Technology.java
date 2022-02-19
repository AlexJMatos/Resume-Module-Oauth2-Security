package com.resume.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "technologies")
public class Technology {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "technology")
	@NotBlank(message = "technology cannot be null or empty")
	private String technology;
	
	@Column(name = "description")
	@NotBlank(message = "description cannot be null or empty")
	private String description;
	
	@Column(name = "technology_employee_id")
	@NotNull(message = "employeeId cannot be null")
	private long employeeId;
}
