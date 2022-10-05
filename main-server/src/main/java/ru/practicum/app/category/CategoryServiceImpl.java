package ru.practicum.app.category;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.util.List;

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

    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (newCategoryDto.getName() == null) {
            throw new CategoryCastomException("данные введены неверно");
        }
        try {
            Category category = categoryRepository.save(categoryMapper.mapNewToCategory(newCategoryDto));
            log.info("категория создана");
            return categoryMapper.mapNewToCategoryDto(category);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new CategoryCastomException("Такая категория уже существует");
        }
    }

    public CategoryDto  update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null && categoryDto.getName() == null) {
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
        categoryRepository.findById(id).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("такой категории не сущетствует", id));
        });
        categoryRepository.deleteById(id);
        log.info("категория удалена");
    }

    public List<CategoryDto> getCategories(@Min(0) int from, @Min(1) int size) {
        int page = from / size;
        Pageable pegable = PageRequest.of(page, size);
        Page<Category> catList = categoryRepository.findAll(pegable);
        List<Category> categoryList = catList.getContent();
        log.info("категория получена");
        return categoryMapper.mapAllToCategoryDto(categoryList);
    }

    public CategoryDto getCategoryById(int catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("Такой категории не сущетствует", catId));
        });
        log.info("категория удалена");
        return categoryMapper.mapToCategoryDto(category);
    }
}


