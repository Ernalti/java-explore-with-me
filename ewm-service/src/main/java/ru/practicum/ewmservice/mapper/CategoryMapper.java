package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;
import ru.practicum.ewmservice.model.Category;

@UtilityClass
public class CategoryMapper {

	public Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto) {
		return Category.builder()
				.name(newCategoryDto.getName())
				.build();
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		return CategoryDto.builder()
				.id(category.getId())
				.name(category.getName())
				.build();
	}
}
