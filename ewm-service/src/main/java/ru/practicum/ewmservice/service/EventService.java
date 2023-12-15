package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewEventDto;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.dto.UpdateEventAdminRequest;
import ru.practicum.ewmservice.dto.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
	List<EventFullDto> findEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

	EventFullDto patchEventById(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

	List<EventShortDto> findEventsForOwner(long userId, int from, int size);

	EventFullDto createEvent(long userId, NewEventDto newEventDto);

	EventFullDto findEventByIdForOwner(long userId, long eventId);

	EventFullDto patchEventByIdForOwner(long userId, long eventId, UpdateEventUserRequest updateEvent);

	List<ParticipationRequestDto> findEventsRequests(long userId, long eventId);

	EventRequestStatusUpdateResult patchEventsRequests(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest);

	List<EventShortDto> findPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size);

	EventFullDto findPublicEventyId(long eventId);

	void addStatsClient(HttpServletRequest request);

}
