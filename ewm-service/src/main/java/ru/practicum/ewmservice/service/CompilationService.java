package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
	CompilationDto createCompilation(NewCompilationDto compilationDto);

	CompilationDto patchCompilation(long compId, NewCompilationDto compilationDto);

	void deleteCompilation(long compId);

	List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

	CompilationDto getCompilationsById(long compId);
}
