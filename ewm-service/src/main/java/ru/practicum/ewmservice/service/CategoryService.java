package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
	CategoryDto createCategory(NewCategoryDto newCategoryDto);

	void deleteCategoryById(Long catId);

	CategoryDto patchCategoryById(Long catId, NewCategoryDto newCategoryDto);

	List<CategoryDto> getCategories(Integer from, Integer size);

	CategoryDto getCategoryById(Long catId);
}
