package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewEventDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.enums.RequestStatus;

@UtilityClass
public class EventMapper {

	public Event newEventDtoToEvent(NewEventDto newEventDto) {
		return Event.builder()
				.id(null)
				.annotation(newEventDto.getAnnotation())
				.description(newEventDto.getDescription())
				.eventDate(newEventDto.getEventDate())
				.location(LocationMapper.locationDtoToLocation(newEventDto.getLocation()))
				.paid(newEventDto.getPaid())
				.participantLimit(newEventDto.getParticipantLimit())
				.requestModeration(newEventDto.getRequestModeration())
				.title(newEventDto.getTitle())
				.build();
	}

	public EventFullDto eventToEventFullDto(Event event) {
		return EventFullDto.builder()
				.annotation(event.getAnnotation())
				.category(CategoryMapper.categoryToCategoryDto(event.getCategory()))
				.confirmedRequests(event.getRequests() == null ? 0 : event.getRequests()
						.stream()
						.filter(x -> x.getStatus().equals(RequestStatus.CONFIRMED))
						.count())
				.createdOn(event.getCreatedOn())
				.description(event.getDescription())
				.eventDate(event.getEventDate())
				.id(event.getId())
				.initiator(UserMapper.userToUserShortDto(event.getInitiator()))
				.location(LocationMapper.locationToLocationDto(event.getLocation()))
				.paid(event.isPaid())
				.participantLimit(event.getParticipantLimit())
				.publishedOn(event.getPublishedDate())
				.requestModeration(event.isRequestModeration())
				.state(event.getState())
				.title(event.getTitle())
				.build();
	}


	public EventShortDto eventToEventShortDto(Event event) {
		return EventShortDto.builder()
				.id(event.getId())
				.annotation(event.getAnnotation())
				.category(CategoryMapper.categoryToCategoryDto(event.getCategory()))
				.eventDate(event.getEventDate())
				.initiator(UserMapper.userToUserShortDto(event.getInitiator()))
				.paid(event.isPaid())
				.title(event.getTitle())
				.publishedDate(event.getPublishedDate())
				.build();
	}
}
