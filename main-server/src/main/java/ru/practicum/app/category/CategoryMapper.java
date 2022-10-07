package ru.practicum.app.category;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
//        categoryDto.setId(category.getCategoryId());
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

    public Category mapNewToCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }


    public CategoryDto mapNewToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getCategoryId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
