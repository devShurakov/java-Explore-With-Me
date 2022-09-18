package ru.practicum.app.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto create(CategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new CategoryCastomException("данные введены неверно");
        }

        Category category = categoryRepository.save(categoryMapper.mapToCategory(categoryDto));
        log.info("категория создана");
        return categoryMapper.mapToCategoryDto(category);
    }

    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null && categoryDto.getId() == 0) {
            throw new CategoryCastomException("данные введены неверно");
        }

        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("такой категории не сущетствует", categoryDto.getId()));
        });

        category.setName(categoryDto.getName());
        log.info("категория обновлена");
        return categoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
        log.info("категория удалена");
    }

    public Page<Category> getCategories(int from, int size) {
        int page = from / size;
        Pageable pegable = PageRequest.of(page, size);
        Page<Category> catList = categoryRepository.findAll(pegable);
        log.info("категория получена");
        return catList;
    }

    public CategoryDto getCategoryById(int catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("Такой категории не сущетствует", catId));
        });
        log.info("категория удалена");
        return categoryMapper.mapToCategoryDto(category);
    }
}


