package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
	List<ParticipationRequestDto> findRequestsForUser(Long userId);

	ParticipationRequestDto createRequest(Long userId, Long eventId);

	ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
