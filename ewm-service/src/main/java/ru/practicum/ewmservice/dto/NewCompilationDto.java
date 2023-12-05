package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class NewCompilationDto {
	Set<Long> events;
	@NotNull
	Boolean pinned = false;
	@NotBlank
	@Size(max = 50)
	String title;

}
