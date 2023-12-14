package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByIdAndAuthorId(long commentId, long userId);

	Page<Comment> findAllByEventId(long eventId, PageRequest pageRequest);

	Page<Comment> findAllByAuthorId(long userId, PageRequest pageRequest);
}
