package ru.practicum.app.service.category;

import ru.practicum.app.dto.category.CategoryDto;
import ru.practicum.app.dto.category.NewCategoryDto;

import javax.validation.constraints.Min;
import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategoryDto);

    CategoryDto  update(CategoryDto categoryDto);

    void delete(int id);

    List<CategoryDto> getCategories(@Min(0) int from, @Min(1) int size);

    CategoryDto getCategoryById(int catId);
}
