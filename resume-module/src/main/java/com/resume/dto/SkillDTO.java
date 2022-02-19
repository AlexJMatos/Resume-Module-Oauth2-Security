package com.resume.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.resume.enums.SkillType;
import com.resume.enums.validator.ValueOfEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_DEFAULT)
@JsonIgnoreProperties(allowGetters = true, value = "id")
@Schema(description = "Data transfer object for skill information in GET, POST and PUT operations")
public class SkillDTO {
	
	private long id;
	
	@NotBlank(message = "skill field cannot be null or empty")
	@Size(min = 1, max = 255, message = "skill field must be between 1 and 255 characters")
	private String skill;
	
	@ValueOfEnum(enumClass = SkillType.class, message = "Invalid type, accepted values: {SOFT_SKILL, HARD_SKILL}")
	@NotNull(message = "type cannot be null")
	private String type;
	
	@NotBlank(message = "description field cannot be null or empty")
	@Size(min = 1, max = 255, message = "description field must be between 1 and 255 characters")
	private String description;
}