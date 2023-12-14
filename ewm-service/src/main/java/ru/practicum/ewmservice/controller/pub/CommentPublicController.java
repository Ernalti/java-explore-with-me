package ru.practicum.ewmservice.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.service.CommentService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@Validated
public class CommentPublicController {

	private final CommentService commentService;

	@Autowired
	public CommentPublicController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/events/{eventId}/comments")
	public List<CommentDto> getAllCommentsForEvent(@PathVariable @Min(1) long eventId,
	                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
												   @RequestParam(defaultValue = "10") @Positive int size) {
		return commentService.getCommentsForEvent(eventId, from, size);
	}

	@GetMapping("/comments/{commentId}")
	public CommentDto getCommentById(@PathVariable @Min(1) long commentId) {
		return commentService.getCommentById(commentId);
	}

}
