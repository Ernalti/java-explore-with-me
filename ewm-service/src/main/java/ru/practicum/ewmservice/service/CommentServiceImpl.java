package ru.practicum.ewmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.dto.CommentRequestDto;
import ru.practicum.ewmservice.exception.exceptions.ConflictException;
import ru.practicum.ewmservice.mapper.CommentMapper;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.enums.EventState;
import ru.practicum.ewmservice.repository.CommentRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(EventRepository eventRepository, UserRepository userRepository, CommentRepository commentRepository) {
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public List<CommentDto> getCommentsForUser(long userId, int from, int size) {
		Sort sort = Sort.by("id");
		PageRequest pageRequest = PageRequest.of(from, size, sort);
		return commentRepository.findAllByAuthorId(userId, pageRequest).toList().stream()
				.map(CommentMapper::commentToDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public CommentDto updateCommentbyAdmin(long commentId, CommentRequestDto commentRequestDto) {
		Comment comment = CommentMapper.dtoToComment(commentRequestDto);
		Comment updatedComment = commentRepository.findById(commentId).orElseThrow();
		mergeComments(updatedComment, comment);
		updatedComment.setUpdated(LocalDateTime.now());
		updatedComment.setModerated(true);
		return CommentMapper.commentToDto(commentRepository.save(updatedComment));

	}

	@Override
	@Transactional
	public void deleteCommentByAdmin(long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Override
	@Transactional
	public void deleteComment(long userId, long commentId) {
		userRepository.findById(userId).orElseThrow();
		commentRepository.findByIdAndAuthorId(commentId,userId).orElseThrow();
		commentRepository.deleteById(commentId);
	}

	@Override
	@Transactional
	public CommentDto createComment(long userId, long eventId, CommentRequestDto commentRequestDto) {
		Comment comment = CommentMapper.dtoToComment(commentRequestDto);
		comment.setAuthor(userRepository.findById(userId).orElseThrow());
		Event event = eventRepository.findById(eventId).orElseThrow();
		if (!event.getState().equals(EventState.PUBLISHED)) {
			throw new ConflictException("The event is not published");
		}
		comment.setEvent(eventRepository.findById(eventId).orElseThrow());
		comment.setCreated(LocalDateTime.now());
		comment.setUpdated(LocalDateTime.now());
		return CommentMapper.commentToDto(commentRepository.save(comment));
	}

	@Override
	@Transactional
	public CommentDto updateComment(long userId, long commentId, CommentRequestDto commentRequestDto) {
		Comment comment = CommentMapper.dtoToComment(commentRequestDto);
		userRepository.findById(userId).orElseThrow();
		Comment updatedComment = commentRepository.findByIdAndAuthorId(commentId,userId).orElseThrow();
		mergeComments(updatedComment, comment);
		updatedComment.setUpdated(LocalDateTime.now());
		return CommentMapper.commentToDto(commentRepository.save(updatedComment));
	}

	@Override
	public List<CommentDto> getCommentsForEvent(long eventId, int from, int size) {
		Sort sort = Sort.by("id");
		PageRequest pageRequest = PageRequest.of(from, size, sort);
		return commentRepository.findAllByEventId(eventId, pageRequest).toList().stream()
				.map(CommentMapper::commentToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> getAllComments(int from, int size) {
		Sort sort = Sort.by("id").descending();
		PageRequest pageRequest = PageRequest.of(from, size, sort);
		return commentRepository.findAll(pageRequest).toList().stream()
				.map(CommentMapper::commentToDto)
				.collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		return CommentMapper.commentToDto(comment);
	}

	private void mergeComments(Comment updatedComment, Comment comment) {
		updatedComment.setText(comment.getText());
	}
}
