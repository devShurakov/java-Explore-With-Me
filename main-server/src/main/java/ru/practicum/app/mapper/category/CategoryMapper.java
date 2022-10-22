package ru.practicum.app.mapper.category;

import ru.practicum.app.model.category.Category;
import ru.practicum.app.dto.category.CategoryDto;
import ru.practicum.app.dto.category.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getCategoryId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static List<CategoryDto> mapAllToCategoryDto(List<Category> categories) {
        List<CategoryDto> returnList = new ArrayList<>();
        for (Category category : categories) {
            returnList.add(mapToCategoryDto(category));
        }
        return returnList;
    }

    public static Category mapNewToCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }


    public static CategoryDto mapNewToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getCategoryId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
