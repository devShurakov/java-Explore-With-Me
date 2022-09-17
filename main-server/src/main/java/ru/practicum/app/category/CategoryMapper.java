package ru.practicum.app.category;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
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
}
