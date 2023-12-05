package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventRequest {

	@Size(min = 20, max = 2000)
	private String annotation;

	private Long category;

	@Size(min = 20, max = 7000)
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@FutureOrPresent
	private LocalDateTime eventDate;

	private LocationDto location;

	private Boolean paid;

	private Integer participantLimit;

	private Boolean requestModeration;

	@Size(min = 3, max = 120)
	private String title;

}
