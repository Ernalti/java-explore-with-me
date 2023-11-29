package ru.practicum.statserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statserver.dto.StatResponceDto;
import ru.practicum.statserver.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {

	@Query("SELECT NEW ru.practicum.statserver.dto.StatResponceDto(s.app, s.uri, COUNT(DISTINCT s.ip))" +
			"FROM Stat s " +
			"WHERE s.created BETWEEN :start AND :end " +
			"AND s.uri IN :uris " +
			"GROUP BY s.app, s.uri " +
			"ORDER BY s.app DESC")
	List<StatResponceDto> findStatsWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

	@Query("SELECT NEW ru.practicum.statserver.dto.StatResponceDto(s.app, s.uri, COUNT(s.ip)) " +
			"FROM Stat s " +
			"WHERE s.created BETWEEN :start AND :end " +
			"AND s.uri IN :uris " +
			"GROUP BY s.app, s.uri " +
			"ORDER BY s.app DESC")
	List<StatResponceDto> findStatsWithNotUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

	@Query("SELECT NEW ru.practicum.statserver.dto.StatResponceDto(s.app, s.uri, COUNT(DISTINCT s.ip))" +
			"FROM Stat s " +
			"WHERE s.created BETWEEN :start AND :end " +
			"GROUP BY s.app, s.uri " +
			"ORDER BY s.app DESC")
	List<StatResponceDto> findStatsWithUniqueIp(LocalDateTime start, LocalDateTime end);

	@Query("SELECT NEW ru.practicum.statserver.dto.StatResponceDto(s.app, s.uri, COUNT(s.ip)) " +
			"FROM Stat s " +
			"WHERE s.created BETWEEN :start AND :end " +
			"GROUP BY s.app, s.uri " +
			"ORDER BY s.app DESC")
	List<StatResponceDto> findStatsWithNotUniqueIp(LocalDateTime start, LocalDateTime end);


}
