package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.dto.CommentRequestDto;

import java.util.List;

public interface CommentService {
	List<CommentDto> getCommentsForUser(long userId, int from, int size);

	CommentDto updateCommentbyAdmin(long commentId, CommentRequestDto commentRequestDto);

	void deleteCommentByAdmin(long commentId);

	CommentDto createComment(long userId, long eventId, CommentRequestDto commentRequestDto);

	CommentDto updateComment(long userId, long commentId, CommentRequestDto commentRequestDto);

	void deleteComment(long userId, long commentId);

	List<CommentDto> getCommentsForEvent(long eventId, int from, int size);

	List<CommentDto> getAllComments(int from, int size);

	CommentDto getCommentById(long commentId);
}
