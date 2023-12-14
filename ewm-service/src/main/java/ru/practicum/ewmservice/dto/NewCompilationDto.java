package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
	private Set<Long> events;


	private Boolean pinned;

	@NotBlank(groups = ValidationGroup.CreateUser.class)
	@Size(max = 50)
	private String title;

}
