package com.resume.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resumes")
public class Resume {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "first_name")
	@NotBlank(message = "First name cannot be null or empty")
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last name cannot be null or empty")
	private String lastName;

	@Column(name = "email")
	@NotBlank(message = "Email cannot be null or empty")
	private String email;

	@Column(name = "phone_number")
	@NotBlank(message = "Phone number cannot be null")
	private String phoneNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "technology_employee_id")
	private Set<Technology> technologies = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_employee_id")
	private Set<Skill> skills = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "education_employee_id")
	private Set<Education> education = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "work_employee_id")
	private Set<WorkExperience> workExperience = new HashSet<>();
}
