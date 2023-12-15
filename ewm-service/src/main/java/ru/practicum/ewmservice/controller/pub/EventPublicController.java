package ru.practicum.ewmservice.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewmservice.util.DateTimeUtil.DATE_TIME_FORMAT_PATTERN;

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
	public List<EventShortDto> findPublicEvents(@RequestParam(required = false) @Size(max = 7000) String text,
	                                            @RequestParam(required = false) List<Long> categories,
	                                            @RequestParam(required = false) Boolean paid,
	                                            @RequestParam(required = false)
	                                            @DateTimeFormat(pattern = DATE_TIME_FORMAT_PATTERN)
	                                            LocalDateTime rangeStart,
	                                            @RequestParam(required = false)
	                                            @DateTimeFormat(pattern = DATE_TIME_FORMAT_PATTERN)
	                                            LocalDateTime rangeEnd,
	                                            @RequestParam(defaultValue = "false") boolean onlyAvailable,
	                                            @RequestParam(defaultValue = "event_date") String sort,
	                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                            @RequestParam(defaultValue = "10") @Positive int size,
	                                            HttpServletRequest request) {
		log.info("Find public events");
		eventService.addStatsClient(request);
		return eventService.findPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
				sort, from, size);
	}

	@GetMapping("/{eventId}")
	public EventFullDto findPublicEventyId(@PathVariable @Positive @Min(1) long eventId,
	                                       HttpServletRequest request) {
		log.info("Find public event by id");
		eventService.addStatsClient(request);
		return eventService.findPublicEventyId(eventId);
	}

}
