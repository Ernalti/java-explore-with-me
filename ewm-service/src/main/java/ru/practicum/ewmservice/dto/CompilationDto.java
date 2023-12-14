package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CompilationDto {

	private Long id;
	private Set<EventShortDto> events;
	private Boolean pinned;
	private String title;

}
