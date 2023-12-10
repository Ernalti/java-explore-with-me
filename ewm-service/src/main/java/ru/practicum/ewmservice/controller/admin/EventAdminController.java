package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.UpdateEventAdminRequest;
import ru.practicum.ewmservice.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@Validated
public class EventAdminController {

	private final EventService eventService;

	@Autowired
	public EventAdminController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public List<EventFullDto> findEvents(@RequestParam(required = false) List<Long> users,
	                                     @RequestParam(required = false) List<String> states,
	                                     @RequestParam(required = false) List<Long> categories,
	                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
	                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
	                                     @PositiveOrZero @RequestParam(defaultValue = "0") int from,
	                                     @Positive @RequestParam(defaultValue = "10") int size) {
		log.info("Find events");
		return eventService.findEvents(users, states, categories, rangeStart, rangeEnd, from, size);
	}

	@PatchMapping("/{eventId}")
	public EventFullDto patchEventById(@PathVariable Long eventId,
	                           @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
		log.info("Patch event by id");
		EventFullDto res = eventService.patchEventById(eventId, updateEventAdminRequest);
		return res;
	}

}
