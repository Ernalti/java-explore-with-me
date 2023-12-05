package ru.practicum.ewmservice.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@Validated
public class CompilationPublicController {

	private final CompilationService compilationService;

	@Autowired
	public CompilationPublicController(CompilationService compilationService) {
		this.compilationService = compilationService;
	}


	@GetMapping
	public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
	                                            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
	                                            @RequestParam(defaultValue = "10") @Positive Integer size) {
		log.info("Get compilations");
		return compilationService.getCompilations(pinned, from, size);
	}

	@GetMapping("/{compId}")
	public CompilationDto getCompilationsById(@PathVariable Long compId) {
		log.info("Get compilation by Id");
		return compilationService.getCompilationsById(compId);
	}

}
