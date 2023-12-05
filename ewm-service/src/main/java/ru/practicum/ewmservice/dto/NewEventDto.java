package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NewEventDto {

	@Size(min = 20, max = 2000)
	@NotBlank
	private String annotation;

	@Positive
	@NotNull
	private Long category;

	@Size(min = 20, max = 7000)
	@NotBlank
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull
	private LocalDateTime eventDate;

	@NotNull
	private LocationDto location;

	@NotNull
	private Boolean paid;

	@NotNull
	private Integer participantLimit;

	@NotNull
	private Boolean requestModeration;

	@Size(min = 3, max = 120)
	@NotBlank
	private String title;

}
