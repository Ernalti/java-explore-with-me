package ru.practicum.statserver.service;

import ru.practicum.statdto.StatDto;
import ru.practicum.statserver.dto.StatResponceDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

	StatDto saveHit(StatDto statDto);

	List<StatResponceDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
