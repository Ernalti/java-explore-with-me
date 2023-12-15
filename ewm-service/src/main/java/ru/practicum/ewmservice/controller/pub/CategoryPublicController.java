package ru.practicum.ewmservice.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@Validated
public class CategoryPublicController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryPublicController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                                       @RequestParam(defaultValue = "10") @Positive int size) {
		log.info("Patch category");
		return categoryService.getCategories(from, size);
	}

	@GetMapping("/{catId}")
	public CategoryDto getCategoryById(@PathVariable long catId) {
		log.info("get category by id");
		return categoryService.getCategoryById(catId);
	}
}
