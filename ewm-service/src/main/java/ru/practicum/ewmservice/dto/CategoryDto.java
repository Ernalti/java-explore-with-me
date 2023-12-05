package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class CategoryDto {

	private Long id;

	@NotBlank
	@Size(min = 1, max = 50)
	private String name;

}
