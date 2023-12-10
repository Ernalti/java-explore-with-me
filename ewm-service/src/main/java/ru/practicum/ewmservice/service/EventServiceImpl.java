package ru.practicum.ewmservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.exception.exceptions.ConditionException;
import ru.practicum.ewmservice.exception.exceptions.ConflictException;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.LocationMapper;
import ru.practicum.ewmservice.mapper.RequestMapper;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.model.enums.*;
import ru.practicum.ewmservice.repository.*;
import ru.practicum.stat_client.StatsClient;
import ru.practicum.statdto.StatDto;
import ru.practicum.statdto.StatResponceDto;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@Configuration
@ComponentScan("ru.practicum.stat_client")
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

	@Value("${server.application.name:ewm-service}")
	private String applicationName;

	private final StatsClient statsClient;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final LocationRepository locationRepository;
	private final EventRepository eventRepository;
	private final RequestRepository requestRepository;

	@Autowired
	public EventServiceImpl(StatsClient statsClient, ObjectMapper objectMapper, UserRepository userRepository, CategoryRepository categoryRepository, LocationRepository locationRepository, EventRepository eventRepository, RequestRepository requestRepository) {
		this.statsClient = statsClient;
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
		this.eventRepository = eventRepository;
		this.requestRepository = requestRepository;
	}


	@Override
	public List<EventFullDto> findEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
		Pageable page = PageRequest.of(from / size, size);
		if (rangeStart.isAfter(rangeEnd)) {
			throw new ValidationException("End time cannot be earlier than start time");
		}

		List<EventState> eventStates = null;

		if (states != null) {
			eventStates = states.stream()
					.map(x -> EventState.valueOf(x))
					.collect(Collectors.toList());
		}

		List<Event> events = eventRepository.searchEvents(users, eventStates, categories, rangeStart, rangeEnd, page).toList();
		return events.stream()
				.map(x -> EventMapper.eventToEventFullDto(x))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public EventFullDto patchEventById(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
		Event event = eventRepository.findById(eventId).orElseThrow();

		if (!event.getState().equals(EventState.PENDING)) {
			throw new ConflictException("Event state has not pending.");
		}

		if (event.getPublishedDate() != null && !event.getEventDate().isAfter(event.getPublishedDate().plusHours(1))) {
			throw new ConditionException("Conditions are not met.");
		}

		if (updateEventAdminRequest.getEventDate() != null && updateEventAdminRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
			throw new ValidationException("The time cannot be earlier than two hours later.");
		}

		if (updateEventAdminRequest.getStateAction() != null) {
			if (updateEventAdminRequest.getStateAction().equals(StateActionAdmin.PUBLISH_EVENT)) {
				event.setState(EventState.PUBLISHED);
				event.setPublishedDate(LocalDateTime.now());
			} else if (updateEventAdminRequest.getStateAction().equals(StateActionAdmin.REJECT_EVENT)) {
				event.setState(EventState.CANCELED);
			}
		}


		Event newEvent = updateEvents(event, updateEventAdminRequest);
		Event eventUPD = eventRepository.save(newEvent);
		return EventMapper.eventToEventFullDto(eventUPD);
	}

	@Override
	public List<EventShortDto> findEventsForOwner(Long userId, Integer from, Integer size) {
//		getUser(userId);
//		if (!userRepository.existsById(userId)) {
//			throw new NotFoundException("Пользователь с id= " + userId + " не найден");
//		}
		Pageable page = PageRequest.of(from / size, size);
		List<Event> events = eventRepository.findAllByInitiatorId(userId, page).toList();
		return events.stream()
				.map(x -> EventMapper.eventToEventShortDto(x))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {

		User user = getUser(userId);
		if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
			throw new ValidationException("The time cannot be earlier than two hours later.");
		}
		Event event = EventMapper.newEventDtoToEvent(newEventDto);
		Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow();
		event.setCategory(category);
		event.setInitiator(user);
		event.setState(EventState.PENDING);
		event.setCreatedOn(LocalDateTime.now());
		if (newEventDto.getLocation() != null) {
			Location location = locationRepository.save(LocationMapper.locationDtoToLocation(newEventDto.getLocation()));
			event.setLocation(location);
		}
		Event newEvent = eventRepository.save(event);
		return EventMapper.eventToEventFullDto(newEvent);
	}

	@Override
	public EventFullDto findEventByIdForOwner(Long userId, Long eventId) {
		getUser(userId);

		Event event = getEventByEventIdAndUserId(eventId, userId);

		if (event == null) {
			throw new EntityNotFoundException("Event" + eventId + "not found");
		}
		return EventMapper.eventToEventFullDto(event);
	}

	@Override
	@Transactional
	public EventFullDto patchEventByIdForOwner(Long userId, Long eventId, UpdateEventUserRequest updateEvent) {
		getUser(userId);

		Event event = getEvent(eventId);

		if (updateEvent.getEventDate() != null && updateEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
			throw new ValidationException("The time cannot be earlier than two hours later.");
		}

		if (event.getState().equals(EventState.PUBLISHED) || event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
			throw new ConditionException("Conditions are not met.");
		}


		if (updateEvent.getStateAction() != null) {

			if (updateEvent.getStateAction().equals(StateActionUser.SEND_TO_REVIEW)) {
				event.setState(EventState.PENDING);
			} else if (updateEvent.getStateAction().equals(StateActionUser.CANCEL_REVIEW)) {
				event.setState(EventState.CANCELED);
			}

		}

		Event newEvent = updateEvents(event, updateEvent);
		Event eventUPD = eventRepository.save(newEvent);
		return EventMapper.eventToEventFullDto(eventUPD);
	}

	@Override
	public List<ParticipationRequestDto> findEventsRequests(Long userId, Long eventId) {
		getUser(userId);
		getEventByEventIdAndUserId(eventId, userId);
		List<Request> requests = requestRepository.findAllByEventId(eventId);
		return requests.stream().map(RequestMapper::requestToDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public EventRequestStatusUpdateResult patchEventsRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
		getUser(userId);
		Event event = getEventByEventIdAndUserId(userId, eventId);

		if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
			throw new ConflictException("this event does not require confirmation");
		}

		int confirmedRequestsCount = requestRepository.countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED);

		if (confirmedRequestsCount >= event.getParticipantLimit()) {
			throw new ConflictException("Participant limit exceeded");
		}

		if (event.getParticipantLimit() == 0 || !event.isRequestModeration()) {
			return new EventRequestStatusUpdateResult();
		}

		List<Request> requests = new ArrayList<>(requestRepository.findAllById(updateRequest.getRequestIds()));

		List<Request> confirmedRequests = new ArrayList<>();
		List<Request> rejectedRequests = new ArrayList<>();

		for (Request requestToConfirm : requests) {
			if (requestToConfirm.getStatus().equals(RequestStatus.PENDING)) {
				requestToConfirm.setStatus(updateRequest.getStatus().equals(
						RequestStatus.CONFIRMED) ? RequestStatus.CONFIRMED : RequestStatus.REJECTED
				);
				if (requestToConfirm.getStatus().equals(RequestStatus.CONFIRMED)) {
					confirmedRequests.add(requestToConfirm);
				} else {
					rejectedRequests.add(requestToConfirm);
				}
			} else {
				throw new ConditionException(String.format("Request {} must be pending", requestToConfirm.getId()));
			}

			if (confirmedRequests.size() == event.getParticipantLimit()) {
				for (Request leftRequest : requests) {
					if (leftRequest.getStatus().equals(RequestStatus.PENDING)) {
						leftRequest.setStatus(RequestStatus.REJECTED);
						rejectedRequests.add(leftRequest);
					}
				}
			}
		}

		eventRepository.save(event);
		List<ParticipationRequestDto> confirmedDto = confirmedRequests.stream()
				.map(RequestMapper::requestToDto)
				.collect(Collectors.toList());

		List<ParticipationRequestDto> rejectedDto = rejectedRequests.stream()
				.map(RequestMapper::requestToDto)
				.collect(Collectors.toList());

		return new EventRequestStatusUpdateResult(confirmedDto, rejectedDto);
	}

	@Override
	public List<EventShortDto> findPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request) {
		Sort sort1 = Sort.valueOf(sort.toUpperCase());
		Pageable page = PageRequest.of(from / size, size);

		if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
			throw new ValidationException("End time cannot be earlier than start time");
		}

		List<Event> events = eventRepository.findEventsByFiltersSortByEventDate(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, page).toList();

		addStatsClient(request);
		Map<Long,Long> idsAndHits = getViews(events);
		List<EventShortDto> eventDto = events.stream()
				.map(EventMapper::eventToEventShortDto)
				.collect(Collectors.toList());
		eventDto.forEach(e -> e.setViews(idsAndHits.getOrDefault(e.getId(),0L)));


		if (sort1.equals(Sort.VIEWS)) {
			return eventDto.stream()
					.sorted(Comparator.comparingLong(EventShortDto::getViews).reversed())
					.collect(Collectors.toList());

		} else {
			return eventDto.stream()
					.sorted(Comparator.comparing(EventShortDto::getPublishedDate).reversed())
					.collect(Collectors.toList());
		}
	}

	@Override
	public EventFullDto findPublicEventyId(Long eventId, HttpServletRequest request) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new EntityNotFoundException("Event" + eventId + "not found"));

		Long views = getEventViews(event);

//		event.setViews(views);

		if (!event.getState().equals(EventState.PUBLISHED)) {
			throw new EntityNotFoundException(String.format("Event %s not found", eventId));
		}

		Event eventUPD = eventRepository.save(event);
		addStatsClient(request);
		EventFullDto res = EventMapper.eventToEventFullDto(eventUPD);
		res.setViews(views);
		return EventMapper.eventToEventFullDto(eventUPD);
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow();
	}

	private Category getCategory(Long catId) {
		return categoryRepository.findById(catId).orElseThrow();
	}

	private Event getEvent(Long eventId) {
		return eventRepository.findById(eventId).orElseThrow();
	}

	private Event getEventByEventIdAndUserId(Long eventId, Long userId) {
		return eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow();
	}

	private Event updateEvents(Event oldEvent, UpdateEventRequest updateEvent) {
		String gotAnnotation = updateEvent.getAnnotation();

		if (gotAnnotation != null && !gotAnnotation.isBlank()) {
			oldEvent.setAnnotation(gotAnnotation);
		}

		Long updateCategory = updateEvent.getCategory();
		if (updateCategory != null) {
			oldEvent.setCategory(getCategory(updateCategory));
		}

		String updateDescription = updateEvent.getDescription();
		if (updateDescription != null && !updateDescription.isBlank()) {
			oldEvent.setDescription(updateDescription);
		}

		LocationDto updateLocation = updateEvent.getLocation();
		if (updateLocation != null) {
			Location location = LocationMapper.locationDtoToLocation(updateLocation);
			oldEvent.setLocation(location);
		}

		Integer updateParticipantLimit = updateEvent.getParticipantLimit();
		if (updateParticipantLimit != null) {
			oldEvent.setParticipantLimit(updateParticipantLimit);
		}

		if (updateEvent.getPaid() != null) {
			oldEvent.setPaid(updateEvent.getPaid());
		}

		if (updateEvent.getRequestModeration() != null) {
			oldEvent.setRequestModeration(updateEvent.getRequestModeration());
		}

		String updateTitle = updateEvent.getTitle();
		if (updateTitle != null && !updateTitle.isBlank()) {
			oldEvent.setTitle(updateTitle);
		}

		return oldEvent;
	}

	private long getEventViews(Event event) {

		ResponseEntity<Object> objectResponseEntity = statsClient.getStatistics(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				List.of("/events/" + event.getId()),
				true);

		String bodyToString = objectResponseEntity.getBody().toString();
		String regex = "hits=(\\d+)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(bodyToString);

		long views = 0;
		if (matcher.find()) {
			String hitsValue = matcher.group(1);
			views = Long.parseLong(hitsValue);
		}
		return views;
	}

	private void addStatsClient(HttpServletRequest request) {
		StatDto hit = StatDto.builder()
				.app(applicationName)
				.uri(request.getRequestURI())
				.ip(request.getRemoteAddr())
				.timestamp(LocalDateTime.now())
				.build();
		statsClient.postHit(hit);
	}

	private Map<Long, Long> getViews(List<Event> events) {
//		statsClient.getStatistics(event.getPublishedDate().minusMinutes(1).toString(), LocalDateTime.now().toString(), List.of(new String[]{("/events/" + event.getId())}), true).getBody();

		if (events.size() == 0) {
			return new HashMap<>();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		List<String> uris = events.stream()
				.map(event -> String.format("/events/%s", event.getId()))
				.collect(Collectors.toList());

		List<LocalDateTime> startDates = events.stream()
				.map(Event::getCreatedOn)
				.collect(Collectors.toList());
		String earliestDate = startDates.stream()
				.min(LocalDateTime::compareTo)
				.orElse(null).format(formatter);
		String now = LocalDateTime.now().format(formatter);
		Map<Long, Long> viewStatsMap = new HashMap<>();

		if (earliestDate != null) {
			ResponseEntity<Object> response = statsClient.getStatistics(earliestDate, now, uris, true);

			List<StatResponceDto> viewStatsList = objectMapper.convertValue(response.getBody(), new TypeReference<>() {
			});

			viewStatsMap = viewStatsList.stream()
					.filter(stats -> stats.getUri().startsWith("/events/"))
					.collect(Collectors.toMap(
							stats -> Long.parseLong(stats.getUri().substring("/events/".length())),
							StatResponceDto::getHits
					));
		}
		return viewStatsMap;
	}
}
