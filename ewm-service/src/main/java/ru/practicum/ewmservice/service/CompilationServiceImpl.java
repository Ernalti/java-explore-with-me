package ru.practicum.ewmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.exception.exceptions.ConditionException;
import ru.practicum.ewmservice.mapper.CompilationMapper;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService{

	private final CompilationRepository compilationRepository;
	private final EventRepository eventRepository;

	@Autowired
	public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
		this.compilationRepository = compilationRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	@Transactional
	public CompilationDto createCompilation(NewCompilationDto compilationDto) {
		Compilation compilation = CompilationMapper.dtoToCompilation(compilationDto);
		compilation.setPinned(compilation.getPinned()!=null && compilation.getPinned());

		Set<Long> compEventIds = (compilationDto.getEvents() != null) ? compilationDto.getEvents() : Collections.emptySet();
		List<Long> eventIds = new ArrayList<>(compEventIds);
		List<Event> events = eventRepository.findAllByIdIn(eventIds);
		Set<Event> eventsSet = new HashSet<>(events);
		compilation.setEvents(eventsSet);

		return CompilationMapper.compilationToDto(compilationRepository.save(compilation));
	}

	@Override
	@Transactional
	public CompilationDto patchCompilation(Long compId, NewCompilationDto compilationDto) {
		Compilation compilation = getCompilation(compId);

		Set<Long> eventIds =  compilationDto.getEvents();

		if (eventIds != null) {
			List<Event> events = eventRepository.findAllByIdIn(new ArrayList<>(eventIds));
			Set<Event> eventSet = new HashSet<>(events);
			compilation.setEvents(eventSet);
		}

		compilation.setPinned(compilation.getPinned()!=null && compilation.getPinned());
		if (compilation.getTitle().isBlank()) {
			throw new ConditionException("Title can't be is blank");
		}
		compilation.setTitle(compilationDto.getTitle()!=null ? compilationDto.getTitle() : compilation.getTitle());

		return CompilationMapper.compilationToDto(compilation);
	}

	@Override
	@Transactional
	public void deleteCompilation(Long compId) {
		getCompilation(compId);
		compilationRepository.deleteById(compId);
	}

	@Override
	public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
		PageRequest pageRequest = PageRequest.of(from, size);
		List<Compilation> compilations;
		if (pinned == null) {
			compilations = compilationRepository.findAll(pageRequest).getContent();
		} else {
			compilations = compilationRepository.findAllByPinned(pinned, pageRequest).toList();
		}

		return compilations.stream()
				.map(CompilationMapper::compilationToDto)
				.collect(Collectors.toList());
	}

	@Override
	public CompilationDto getCompilationsById(Long compId) {
		return CompilationMapper.compilationToDto(getCompilation(compId));
	}

	private Compilation getCompilation(Long compId) {
		return compilationRepository.findById(compId).orElseThrow();
	}
}
