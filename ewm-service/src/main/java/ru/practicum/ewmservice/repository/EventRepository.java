package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
	Page<Event> findAllByInitiatorId(Long userId, Pageable page);

	@Query("SELECT e FROM Event e " +
			"WHERE (:users IS NULL OR e.initiator.id IN :users) " +
			"AND (:eventStates IS NULL OR e.state IN :eventStates) " +
			"AND (:categories IS NULL OR e.category.id IN :categories) " +
			"AND (CAST(:rangeStart AS date) IS NULL OR e.eventDate >= :rangeStart) " +
			"AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd) ")
	Page<Event> searchEvents(List<Long> users,
	                         List<EventState> eventStates,
	                         List<Long> categories,
	                         LocalDateTime rangeStart,
	                         LocalDateTime rangeEnd,
	                         Pageable page);



	Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

//	@Query("SELECT e FROM Event e " +
//			"WHERE e.state = 'PUBLISHED' " +
//			"AND (:text IS NULL OR (LOWER(e.annotation) LIKE LOWER(:text) OR LOWER(e.description) LIKE LOWER(:text))) " +
//			"AND (:categories IS NULL OR e.category.id IN :categories) " +
//			"AND (:paid IS NULL OR e.paid = :paid) " +
//			"AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
//			"AND (:onlyAvailable = true AND size(e.requests) < e.participantLimit " +
//			"     OR :onlyAvailable = false) " +
//			"ORDER BY e.views DESC")
//	Page<Event> findEventsByFiltersSortByViews(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable page);

	@Query("SELECT e FROM Event e " +
			"WHERE e.state = 'PUBLISHED' " +
			"AND (:text IS NULL OR (LOWER(e.annotation) LIKE LOWER(:text) OR LOWER(e.description) LIKE LOWER(:text))) " +
			"AND (:categories IS NULL OR e.category.id IN :categories) " +
			"AND (:paid IS NULL OR e.paid = :paid) " +
			"AND (CAST(:rangeStart AS date) IS NULL OR e.eventDate >= :rangeStart) " +
			"AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd) " +
			"AND (:onlyAvailable = true AND size(e.requests) < e.participantLimit " +
			"     OR :onlyAvailable = false) " +
			"ORDER BY e.eventDate DESC")
	Page<Event> findEventsByFiltersSortByEventDate(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable page);

	List<Event> findAllByIdIn(List<Long> eventIds);
}
