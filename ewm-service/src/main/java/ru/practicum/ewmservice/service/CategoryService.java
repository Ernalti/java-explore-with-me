package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
	CategoryDto createCategory(NewCategoryDto newCategoryDto);

	void deleteCategoryById(long catId);

	CategoryDto patchCategoryById(long catId, NewCategoryDto newCategoryDto);

	List<CategoryDto> getCategories(int from, int size);

	CategoryDto getCategoryById(long catId);
}
