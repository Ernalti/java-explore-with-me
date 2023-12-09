package ru.practicum.statserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statdto.StatDto;
import ru.practicum.statdto.StatResponceDto;
import ru.practicum.statserver.exception.IllegalTimeException;
import ru.practicum.statserver.mapper.StatMapper;
import ru.practicum.statserver.model.Stat;
import ru.practicum.statserver.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {

	private final StatRepository statRepository;

	@Autowired
	public StatServiceImpl(StatRepository statRepository) {
		this.statRepository = statRepository;
	}

	@Override
	@Transactional
	public StatDto saveHit(StatDto statDto) {
		Stat stats = StatMapper.statDtoToStat(statDto);
		log.info("Добавление статистики {}", stats);
		return StatMapper.statToStatDto(statRepository.save(stats));
	}

	@Override
	public List<StatResponceDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
		if (!start.isBefore(end)) {
			throw new IllegalTimeException("Start time can't be before end time.");
		}

		if (uris == null || uris.isEmpty()) {
			if (unique) {
				return statRepository.findStatsWithUniqueIp(start, end);
			} else {
				return statRepository.findStatsWithNotUniqueIp(start, end);
			}
		} else {
			if (unique) {
				return statRepository.findStatsWithUniqueIp(start, end, uris);
			} else {
				return statRepository.findStatsWithNotUniqueIp(start, end, uris);
			}
		}
	}
}
