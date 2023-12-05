package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.LocationDto;
import ru.practicum.ewmservice.model.Location;

@UtilityClass
public class LocationMapper {

	public Location locationDtoToLocation(LocationDto locationDto) {
		return Location.builder()
				.lat(locationDto.getLat())
				.lon(locationDto.getLon())
				.build();
	}


	public static LocationDto locationToLocationDto(Location location) {
		return LocationDto.builder()
				.lat(location.getLat())
				.lon(location.getLon())
				.build();
	}
}
