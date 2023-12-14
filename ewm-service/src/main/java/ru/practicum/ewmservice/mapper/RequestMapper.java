package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.model.Request;

@UtilityClass
public class RequestMapper {

	public ParticipationRequestDto requestToDto(Request request) {
		return ParticipationRequestDto.builder()
				.id(request.getId())
				.event(request.getEvent().getId())
				.created(request.getCreated())
				.requester(request.getRequester().getId())
				.status(String.valueOf(request.getStatus()))
				.build();
	}
}
