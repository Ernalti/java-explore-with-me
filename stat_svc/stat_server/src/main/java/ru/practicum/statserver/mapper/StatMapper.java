package ru.practicum.statserver.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.statdto.StatDto;
import ru.practicum.statserver.model.Stat;

@UtilityClass
public class StatMapper {

	public Stat statDtoToStat(StatDto statDto) {
		return Stat.builder()
				.app(statDto.getApp())
				.uri(statDto.getUri())
				.ip(statDto.getIp())
				.created(statDto.getTimestamp())
				.build();
	}

	public StatDto statToStatDto(Stat stat) {
		return StatDto.builder()
				.id(stat.getId())
				.app(stat.getApp())
				.uri(stat.getUri())
				.ip(stat.getIp())
				.timestamp(stat.getCreated())
				.build();
	}

}
