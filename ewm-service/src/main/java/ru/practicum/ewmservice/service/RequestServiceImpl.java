package ru.practicum.ewmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.exception.exceptions.ConflictException;
import ru.practicum.ewmservice.exception.exceptions.NotFoundException;
import ru.practicum.ewmservice.mapper.RequestMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.Request;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.model.enums.EventState;
import ru.practicum.ewmservice.model.enums.RequestStatus;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.RequestRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final RequestRepository requestRepository;

	@Autowired
	public RequestServiceImpl(UserRepository userRepository, EventRepository eventRepository, RequestRepository requestRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.requestRepository = requestRepository;
	}


	@Override
	public List<ParticipationRequestDto> findRequestsForUser(Long userId) {
		List<Request> result = requestRepository.findAllByRequesterId(userId);
		return result.stream().map(RequestMapper::requestToDto).collect(Collectors.toList());
	}

	@Override
	public ParticipationRequestDto createRequest(Long userId, Long eventId) {
		User user = getUser(userId);

		Event event = eventRepository.findById(eventId).orElseThrow();
		LocalDateTime createdOn = LocalDateTime.now();

		if (event.getInitiator().getId().equals(userId)) {
			throw new ConflictException("User " + userId + " not found");
		}
		if (event.getParticipantLimit() > 0 && event.getParticipantLimit() <= requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)) {
			throw new ConflictException("Participants limit is reached");
		}
		if (!event.getState().equals(EventState.PUBLISHED)) {
			throw new ConflictException("The event is not published");
		}
		if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
			throw new ConflictException("The user has already sent the request");
		}

		Request request = new Request();
		request.setCreated(createdOn);
		request.setRequester(user);
		request.setEvent(event);

		if (event.isRequestModeration()) {
			request.setStatus(RequestStatus.PENDING);
		} else {
			request.setStatus(RequestStatus.CONFIRMED);
		}

		requestRepository.save(request);

		if (event.getParticipantLimit() == 0) {
			request.setStatus(RequestStatus.CONFIRMED);
		}

		return RequestMapper.requestToDto(request);
	}

	@Override
	public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
		getUser(userId);
		Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow();
		if (request.getStatus().equals(RequestStatus.CANCELED) || request.getStatus().equals(RequestStatus.REJECTED)) {
			throw new ConflictException("Request not confirmed");
		}
		request.setStatus(RequestStatus.CANCELED);
		Request requestAfterSave = requestRepository.save(request);
		return RequestMapper.requestToDto(requestAfterSave);
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() ->
				new NotFoundException("Категории с id = " + userId + " не существует"));
	}
}
