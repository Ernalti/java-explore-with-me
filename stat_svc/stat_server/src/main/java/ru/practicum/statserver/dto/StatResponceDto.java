package ru.practicum.statserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StatResponceDto {

	private String app;

	private String uri;

	private long hits;

}
