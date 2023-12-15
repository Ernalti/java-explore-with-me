package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.CountComments;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByIdAndAuthorId(long commentId, long userId);

	Page<Comment> findAllByEventId(long eventId, PageRequest pageRequest);

	Page<Comment> findAllByAuthorId(long userId, PageRequest pageRequest);

	@Query("SELECT COUNT(c) FROM Comment c WHERE c.event.id = :eventId")
	long countCommentsByEventId(long eventId);

	@Query("select new ru.practicum.ewmservice.model.CountComments(c.event.id, COUNT(c)) " +
			"from Comment as c where c.event.id IN :eventIds " +
			"GROUP BY c.event.id")
	List<CountComments> countCommentsByEventIds(List<Long> eventIds);

}
