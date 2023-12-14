package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
	List<ParticipationRequestDto> findRequestsForUser(long userId);

	ParticipationRequestDto createRequest(long userId, long eventId);

	ParticipationRequestDto cancelRequest(long userId, long requestId);
}
