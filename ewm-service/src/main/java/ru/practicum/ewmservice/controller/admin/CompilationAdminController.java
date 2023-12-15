package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.dto.ValidationGroup;
import ru.practicum.ewmservice.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
	@Validated(ValidationGroup.CreateUser.class)
	public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
		log.info("Add compilation");
		return compilationService.createCompilation(compilationDto);
	}

	@PatchMapping("/{compId}")
	public CompilationDto patchCompilation(@RequestBody @Valid NewCompilationDto compilationDto,
	                                       @PathVariable @Min(1) long compId) {
		log.info("patch compilation");
		return compilationService.patchCompilation(compId, compilationDto);
	}

	@DeleteMapping("/{compId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCompilation(@PathVariable @Min(1) long compId) {
		log.info("delete compilation");
		compilationService.deleteCompilation(compId);
	}
}
