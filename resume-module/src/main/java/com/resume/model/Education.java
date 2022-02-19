package com.resume.model;

import java.util.Date;

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
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.resume.enums.DegreeType;

import lombok.Data;

@Entity
@Data
@Table(name = "education")
public class Education {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "degree")
	@NotBlank(message = "degree must not be null or empty")
	private String degree;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private DegreeType type;
	
	@Column(name = "from_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@PastOrPresent(message = "fromDate cannot be a future date")
	@NotNull
	private Date fromDate;
	
	@Column(name = "until_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date untilDate;
	
	@Column(name = "education_employee_id")
	@NotNull(message = "the employee id cannot be null")
	private long employeeId;
}
