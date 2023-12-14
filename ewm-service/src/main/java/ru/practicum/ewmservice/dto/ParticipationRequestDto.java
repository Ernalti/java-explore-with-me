package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.ewmservice.util.DateTimeUtil.DATE_TIME_FORMAT_PATTERN;

@Data
@AllArgsConstructor
@Builder
public class ParticipationRequestDto {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_PATTERN)
	private LocalDateTime created;

	private Long event;

	private Long id;

	private Long requester;

	private String status;

}
