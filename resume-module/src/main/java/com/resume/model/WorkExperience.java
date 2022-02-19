package com.resume.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "work_experience")
public class WorkExperience {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "position")
	@NotBlank(message = "position must not be null or empty")
	private String position;
	
	@Column(name = "company")
	@NotBlank(message = "company must not be null or empty")
	private String company;
	
	@Column(name = "description")
	@NotBlank(message = "description must not be null or empty")
	private String description;
	
	@Column(name = "from_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@PastOrPresent(message = "fromDate cannot be a future date")
	@NotNull
	private Date fromDate;
	
	@Column(name = "until_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date untilDate;
	
	@Column(name = "work_employee_id")
	@NotNull(message = "the employee id cannot be null")
	private long employeeId;
}
