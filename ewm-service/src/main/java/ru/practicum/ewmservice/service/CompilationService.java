package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
	CompilationDto createCompilation(NewCompilationDto compilationDto);

	CompilationDto patchCompilation(Long compId, NewCompilationDto compilationDto);

	void deleteCompilation(Long compId);

	List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

	CompilationDto getCompilationsById(Long compId);
}
