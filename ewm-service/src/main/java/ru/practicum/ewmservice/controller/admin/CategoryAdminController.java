package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;
import ru.practicum.ewmservice.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@Validated
public class CategoryAdminController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryAdminController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
		log.info("Create category");
		return categoryService.createCategory(newCategoryDto);
	}

	@DeleteMapping("/{catId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoryById(@PathVariable Long catId) {
		log.info("Delete category by id");
		categoryService.deleteCategoryById(catId);
	}

	@PatchMapping("/{catId}")
	public CategoryDto patchCategoryById(@PathVariable Long catId, @Valid @RequestBody NewCategoryDto newCategoryDto) {
		log.info("Patch category by id");
		return categoryService.patchCategoryById(catId, newCategoryDto);

	}
}
