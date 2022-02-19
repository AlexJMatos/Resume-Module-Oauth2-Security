package com.resume.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "street")
	@NotBlank(message = "street field cannot be null or empty")
	private String street;
	
	@Column(name = "city")
	@NotBlank(message = "city field cannot be null or empty")
	private String city;
	
	@Column(name = "state")
	@NotBlank(message = "state field cannot be null or empty")
	private String state;
	
	@Column(name = "country")
	@NotBlank(message = "country field cannot be null or empty")
	private String country;
	
	@Column(name = "zip_code")
	@NotBlank(message = "zipCode field cannot be null or empty")
	private String zipCode;	
}
