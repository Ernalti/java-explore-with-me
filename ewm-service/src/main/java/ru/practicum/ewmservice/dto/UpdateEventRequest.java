package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewmservice.util.DateTimeUtil.DATE_TIME_FORMAT_PATTERN;

@Data
public class UpdateEventRequest {

	@Size(min = 20, max = 2000)
	private String annotation;

	private Long category;

	@Size(min = 20, max = 7000)
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	@FutureOrPresent
	private LocalDateTime eventDate;

	private LocationDto location;

	private Boolean paid;

	private Integer participantLimit;

	private Boolean requestModeration;

	@Size(min = 3, max = 120)
	private String title;

}
