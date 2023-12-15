package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
	private Long id;

	private String text;

	private Long eventId;

	private Long authorId;

	private LocalDateTime created;

	private LocalDateTime updated;

	private boolean moderated;
}
