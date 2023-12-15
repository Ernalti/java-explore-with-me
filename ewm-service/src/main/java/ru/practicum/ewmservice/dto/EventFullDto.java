package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.model.enums.EventState;

import java.time.LocalDateTime;

import static ru.practicum.ewmservice.util.DateTimeUtil.DATE_TIME_FORMAT_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

	private Long id;
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	private LocalDateTime createdOn;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	private LocalDateTime eventDate;
	private UserShortDto initiator;
	private LocationDto location;
	private Boolean paid;
	private Integer participantLimit;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	private LocalDateTime publishedOn;
	private Boolean requestModeration;
	private EventState state;
	private String title;
	private Long views;
	private Long comments;

}
