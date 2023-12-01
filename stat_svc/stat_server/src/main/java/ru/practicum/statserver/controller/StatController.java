package ru.practicum.statserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statdto.StatDto;
import ru.practicum.statserver.dto.StatResponceDto;
import ru.practicum.statserver.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@Validated
public class StatController {

	private final StatService statService;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	public StatController(StatService statService) {
		this.statService = statService;
	}

	@PostMapping("/hit")
	@ResponseStatus(HttpStatus.CREATED)
	public StatDto saveHit(@Valid @RequestBody StatDto statDto) {
		log.info("Поступил запрос на добавление статистики: {}", statDto);
		return statService.saveHit(statDto);
	}

	@GetMapping("/stats")
	public List<StatResponceDto> getStats(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
	                                      @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
	                                      @RequestParam(required = false) List<String> uris,
	                                      @RequestParam(defaultValue = "false") boolean unique) {
		log.info("Поступил запрос на получение статистики uris:{}", uris);
		return statService.getStats(start, end, uris, unique);
	}
}
