package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping(path = "/admin")
@Validated
public class CommentAdminController {

	private final CommentService commentService;

	@Autowired
	public CommentAdminController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/user/{userId}/comments")
	public List<CommentDto> getCommentsForUser(@PathVariable @Min(1) long userId,
	                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                            @RequestParam(defaultValue = "10") @Positive int size){
		log.info("Get comments from admin");

		return commentService.getCommentsForUser(userId, from, size);
	}

	@GetMapping("/comments")
	public List<CommentDto> getAllComments(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                       @RequestParam(defaultValue = "10") @Positive int size){
		log.info("Get all comments from admin");

		return commentService.getAllComments(from, size);
	}

	@PatchMapping("/comments/{commentId}")
	public CommentDto updateCommentbyAdmin(@PathVariable @Min(1) long commentId,
	                                @Valid @RequestBody CommentRequestDto commentRequestDto) {
		log.info("Update comment from admin");
		return commentService.updateCommentbyAdmin(commentId, commentRequestDto);
	}

	@DeleteMapping("/comments/{commentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteComment(@PathVariable @Min(1) long commentId) {
		log.info("Delete comment from admin");
		commentService.deleteCommentByAdmin(commentId);
	}

}
