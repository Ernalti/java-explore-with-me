package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.ewmservice.util.DateTimeUtil.DATE_TIME_FORMAT_PATTERN;

@Data
@AllArgsConstructor
@Builder
public class EventShortDto {

	private Long id;
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	private LocalDateTime eventDate;
	private UserShortDto initiator;
	private Boolean paid;
	private String title;
	private Long views;

	@JsonIgnore
	private LocalDateTime publishedDate;

}
