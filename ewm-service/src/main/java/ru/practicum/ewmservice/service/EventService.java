package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
	List<EventFullDto> findEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

	EventFullDto patchEventById(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

	List<EventShortDto> findEventsForOwner(Long userId, Integer from, Integer size);

	EventFullDto createEvent(Long userId, NewEventDto newEventDto);

	EventFullDto findEventByIdForOwner(Long userId, Long eventId);

	EventFullDto patchEventByIdForOwner(Long userId, Long eventId, UpdateEventUserRequest updateEvent);

	List<ParticipationRequestDto> findEventsRequests(Long userId, Long eventId);

	EventRequestStatusUpdateResult patchEventsRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);

	List<EventShortDto> findPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request);

	EventFullDto findPublicEventyId(Long eventId, HttpServletRequest request);
}
