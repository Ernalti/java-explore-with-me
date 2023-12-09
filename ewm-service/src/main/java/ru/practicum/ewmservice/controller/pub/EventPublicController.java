package ru.practicum.ewmservice.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@Validated
public class EventPublicController {

	private final EventService eventService;

	@Autowired
	public EventPublicController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public List<EventShortDto> findPublicEvents(@RequestParam(required = false) String text,
	                                            @RequestParam(required = false) List<Long> categories,
	                                            @RequestParam(required = false) Boolean paid,
	                                            @RequestParam(required = false)
	                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	                                 LocalDateTime rangeStart,
	                                            @RequestParam(required = false)
	                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	                                 LocalDateTime rangeEnd,
	                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
	                                            @RequestParam(defaultValue = "event_date") String sort,
	                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                            @RequestParam(defaultValue = "10") @Positive int size,
			                                    HttpServletRequest request) {
		log.info("Find public events");
		return eventService.findPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
				sort, from, size, request);
	}

	@GetMapping("/{eventId}")
	public EventFullDto findPublicEventyId(@PathVariable @Positive Long eventId,
	                                       HttpServletRequest request) {
		log.info("Find public event by id");
		return eventService.findPublicEventyId(eventId, request);
	}

}
