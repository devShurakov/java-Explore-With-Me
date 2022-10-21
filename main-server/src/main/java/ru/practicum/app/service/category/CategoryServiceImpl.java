package ru.practicum.app.service.category;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.app.model.category.Category;
import ru.practicum.app.mapper.category.CategoryMapper;
import ru.practicum.app.repository.category.CategoryRepository;
import ru.practicum.app.dto.category.CategoryDto;
import ru.practicum.app.dto.category.NewCategoryDto;
import ru.practicum.app.exception.CategoryCastomException;
import ru.practicum.app.exception.CategoryNotFoundException;

import javax.validation.constraints.Min;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (newCategoryDto.getName() == null) {
            throw new CategoryCastomException("данные введены неверно");
        }
        try {
            Category category = categoryRepository.save(CategoryMapper.mapNewToCategory(newCategoryDto));
            log.info("категория создана");
            return CategoryMapper.mapNewToCategoryDto(category);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new CategoryCastomException("Такая категория уже существует");
        }
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null && categoryDto.getName() == null) {
            throw new CategoryCastomException("данные введены неверно");
        }

        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("такой категории не сущетствует", categoryDto.getId()));
        });

        category.setName(categoryDto.getName());
        log.info("категория обновлена");
        return CategoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void delete(int id) {
        categoryRepository.findById(id).orElseThrow(() -> {
            throw new CategoryNotFoundException(String.format("такой категории не сущетствует", id));
        });
        categoryRepository.deleteById(id);
        log.info("категория удалена");
    }

    @Override
    public List<CategoryDto> getCategories(@Min(0) int from, @Min(1) int size) {
        int page = from / size;
        Pageable pegable = PageRequest.of(page, size);
        Page<Category> catList = categoryRepository.findAll(pegable);
        List<Category> categoryList = catList.getContent();
        log.info("категория получена");
        return CategoryMapper.mapAllToCategoryDto(categoryList);
    }

    @Override
    public CategoryDto getCategoryById(int catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new CategoryNotFoundException("Такой категории не сущетствует");
        });
        log.info("категория удалена");
        return CategoryMapper.mapToCategoryDto(category);
    }
}


