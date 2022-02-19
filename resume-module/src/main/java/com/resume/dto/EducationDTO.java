package com.resume.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.resume.enums.DegreeType;
import com.resume.enums.validator.ValueOfEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_DEFAULT)
@JsonIgnoreProperties(allowGetters = true, value = "id")
@Schema(description = "Data transfer object for education degree information in GET, POST and PUT operations")
public class EducationDTO {

	private long id;

	@NotBlank(message = "degree field cannot be null or empty")
	@Size(min = 1, max = 255, message = "degree field must be between 1 and 255 characters")
	private String degree;

	@ValueOfEnum(enumClass = DegreeType.class, message = "Invalid type, accepted values: {Certificate, Diploma, Associate, Bachelor, Specialization, Master, Doctoral}")
	@NotNull(message = "type must not be null or empty")
	private String type;

	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@PastOrPresent(message = "fromDate cannot be a future date")
	@NotNull(message = "fromDate must not be null or empty")
	private Date fromDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date untilDate;
}
