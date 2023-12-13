package ru.practicum.ewmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.NewCategoryDto;
import ru.practicum.ewmservice.exception.exceptions.ConflictException;
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final EventRepository eventRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, EventRepository eventRepository) {
		this.categoryRepository = categoryRepository;

		this.eventRepository = eventRepository;
	}

	@Override
	@Transactional
	public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
		Category category = CategoryMapper.newCategoryDtoToCategory(newCategoryDto);
		return CategoryMapper.categoryToCategoryDto(categoryRepository.save(category));
	}

	@Override
	@Transactional
	public void deleteCategoryById(long catId) {
		if (eventRepository.countByCategoryId(catId) > 0L) {
			throw new ConflictException("All events must be delete");
		}
		categoryRepository.deleteById(catId);
	}

	@Override
	@Transactional
	public CategoryDto patchCategoryById(long catId, NewCategoryDto newCategoryDto) {
		Category category = CategoryMapper.newCategoryDtoToCategory(newCategoryDto);
		Category oldCategory = getCategory(catId);
		oldCategory.setName(category.getName());
		return CategoryMapper.categoryToCategoryDto(categoryRepository.save(oldCategory));
	}

	@Override
	public List<CategoryDto> getCategories(int from, int size) {
		Sort sort = Sort.by("name").descending();
		Pageable page = PageRequest.of(from / size, size, sort);
		return categoryRepository.findAll(page).stream().map(CategoryMapper::categoryToCategoryDto).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(long catId) {
		return CategoryMapper.categoryToCategoryDto(getCategory(catId));
	}

	private Category getCategory(long catId) {
		return categoryRepository.findById(catId).orElseThrow();
	}
}
