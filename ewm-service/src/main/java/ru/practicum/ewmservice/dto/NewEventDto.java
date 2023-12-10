package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {

	@Size(min = 20, max = 2000)
	@NotBlank
	private String annotation;

	@Positive
	@NotNull
	private Long category;

	@NotBlank
	@Size(min = 20, max = 7000)
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull
	private LocalDateTime eventDate;

	@NotNull
	private LocationDto location;

	@Builder.Default
	@NotNull
	private Boolean paid = false;

	@Builder.Default
	@NotNull
	private Integer participantLimit = 0;

	@Builder.Default
	@NotNull
	private Boolean requestModeration = true;

	@Size(min = 3, max = 120)
	@NotBlank
	private String title;

}
