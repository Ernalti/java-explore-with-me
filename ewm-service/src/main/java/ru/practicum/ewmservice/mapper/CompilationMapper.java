package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;

import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

	public Compilation dtoToCompilation(NewCompilationDto compilationDto) {
		return Compilation.builder()
				.pinned(compilationDto.getPinned())
				.title(compilationDto.getTitle())
				.build();
	}

	public CompilationDto compilationToDto (Compilation compilation) {
		return CompilationDto.builder()
				.id(compilation.getId())
				.events(compilation.getEvents().stream()
						.map(EventMapper::eventToEventShortDto)
						.collect(Collectors.toSet()))
				.pinned(compilation.getPinned())
				.title(compilation.getTitle())
				.build();
	}

}
