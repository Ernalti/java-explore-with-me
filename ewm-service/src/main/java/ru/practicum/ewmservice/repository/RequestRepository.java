package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Request;
import ru.practicum.ewmservice.model.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
	List<Request> findAllByEventId(Long eventId);

	int countByEventIdAndStatus(Long id, RequestStatus requestStatus);

	Request findByRequesterIdAndEventId(Long userId, Long eventId);

	Boolean existsByEventIdAndRequesterId(Long eventId, Long userId);

	List<Request> findAllByRequesterId(Long userId);

	Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);
}
