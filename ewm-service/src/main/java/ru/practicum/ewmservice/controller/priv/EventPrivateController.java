package ru.practicum.ewmservice.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class EventPrivateController {

	private final EventService eventService;

	@Autowired
	public EventPrivateController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public List<EventShortDto> findEventsForOwner(@PathVariable Long userId,
	                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
	                                              @RequestParam(defaultValue = "10") @Positive Integer size) {
		log.info("Find events for owner {}", userId);
		return eventService.findEventsForOwner(userId, from, size);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EventFullDto createEvent(@PathVariable Long userId,
	                                @RequestBody @Valid NewEventDto newEventDto) {
		log.info("Create event");
		return eventService.createEvent(userId, newEventDto);
	}

	@GetMapping("/{eventId}")
	public EventFullDto findEventByIdForOwner(@PathVariable Long userId,
	                                          @PathVariable Long eventId) {
		log.info("Find event by id for owner");
		return eventService.findEventByIdForOwner(userId, eventId);
	}

	@PatchMapping("/{eventId}")
	public EventFullDto patchEventByIdForOwner(@PathVariable Long userId,
			                                   @PathVariable Long eventId,
			                                   @RequestBody @Valid UpdateEventUserRequest updateEvent) {
		log.info("Patch event by id for owner");
		return eventService.patchEventByIdForOwner(userId, eventId, updateEvent);
	}

	@GetMapping("{eventId}/requests")
	public List<ParticipationRequestDto> findEventsRequests(@PathVariable Long userId,
	                                                        @PathVariable Long eventId) {
		log.info("Find events request");
		return eventService.findEventsRequests(userId, eventId);
	}

	@PatchMapping("{eventId}/requests")
	public EventRequestStatusUpdateResult patchEventsRequests(@PathVariable Long userId,
	                                                          @PathVariable Long eventId,
	                                                          @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
		log.info("Patch events request");
		return eventService.patchEventsRequests(userId, eventId, updateRequest);
	}

}
