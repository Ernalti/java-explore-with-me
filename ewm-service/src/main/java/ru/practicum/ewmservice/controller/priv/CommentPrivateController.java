package ru.practicum.ewmservice.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.dto.CommentRequestDto;
import ru.practicum.ewmservice.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}")
@Validated
public class CommentPrivateController {

	private final CommentService commentService;

	@Autowired
	public CommentPrivateController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/events/{eventId}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentDto createComment(@PathVariable @Min(1) long userId,
	                                @PathVariable @Min(1) long eventId,
	                                @Valid @RequestBody CommentRequestDto commentRequestDto) {
		log.info("Create comment");
		return commentService.createComment(userId, eventId, commentRequestDto);
	}

	@PatchMapping("/comments/{commentId}")
	public CommentDto updateComment(@PathVariable @Min(1) long userId,
	                                  @PathVariable @Min(1) long commentId,
	                                  @Valid @RequestBody CommentRequestDto commentRequestDto) {
		log.info("Update comment");
		return commentService.updateComment(userId, commentId, commentRequestDto);
	}

	@DeleteMapping("/comments/{commentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteComment(@PathVariable @Min(1) long userId,
	                                  @PathVariable @Min(1) long commentId) {
		log.info("Delete comment");
		commentService.deleteComment(userId, commentId);
	}

	@GetMapping("/comments")
	public List<CommentDto> getCommentsForUser(@PathVariable @Min(1) long userId,
	                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                            @RequestParam(defaultValue = "10") @Positive int size) {
		log.info("Get comments");
		return commentService.getCommentsForUser(userId, from, size);
	}


}
