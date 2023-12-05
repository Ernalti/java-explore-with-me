package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
public class CompilationAdminController {

	private final CompilationService compilationService;

	@Autowired
	public CompilationAdminController(CompilationService compilationService) {
		this.compilationService = compilationService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
		log.info("Add compilation");
		return compilationService.createCompilation(compilationDto);
	}

	@PatchMapping("/{compId}")
	public CompilationDto patchCompilation(@RequestBody @Valid NewCompilationDto compilationDto,
	                                       @PathVariable Long compId) {
		log.info("patch compilation");
		return compilationService.patchCompilation(compId, compilationDto);
	}

	@DeleteMapping("/{compId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCompilation(@PathVariable Long compId) {
		log.info("delete compilation");
		compilationService.deleteCompilation(compId);
	}
}
