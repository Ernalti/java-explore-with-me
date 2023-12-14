package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.dto.CommentRequestDto;
import ru.practicum.ewmservice.model.Comment;

@UtilityClass
public class CommentMapper {

	public CommentDto commentToDto(Comment comment){
		return CommentDto.builder()
				.id(comment.getId())
				.text(comment.getText())
				.authorId(comment.getAuthor().getId())
				.eventId(comment.getEvent().getId())
				.created(comment.getCreated())
				.updated(comment.getUpdated())
				.moderated(comment.isModerated())
				.build();
	}

	public Comment dtoToComment(CommentRequestDto commentRequestDto){
		return Comment.builder()
				.text(commentRequestDto.getText())
				.moderated(false)
				.build();
	}

}
