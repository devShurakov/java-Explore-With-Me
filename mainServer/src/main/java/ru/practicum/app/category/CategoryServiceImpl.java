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
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto create(CategoryDto categoryDto) {
        if(categoryDto.getName() == null){
            throw new CategoryCastomException("данные введены неверно");
        }
        Category category = categoryRepository.save(categoryMapper.mapToCategory(categoryDto));
        log.info("категория создана");
        return categoryMapper.mapToCategoryDto(category);
    }

    public CategoryDto update(CategoryDto categoryDto) {
        if(categoryDto.getName() == null){
            throw new CategoryCastomException("данные введены неверно");
        }
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("такой категории не сущетствует", categoryDto.getId()));
        });

        if (category.getName().equals(categoryDto.getName())) {
            throw new CategoryCastomException(String.format("такая категория уже сущетствует", categoryDto.getId()));
        }else{
            category.setName(categoryDto.getName());
            return categoryMapper.mapToCategoryDto(categoryRepository.save(category));
        }
    }

    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    public Page<Category> getCategories(int from, int size) {
        Pageable page = PageRequest.of(from, size);
        Page<Category> catList = categoryRepository.findAll(page);

        return catList;
    }

    public CategoryDto getCategoryById(int catId) {
        Category category = categoryRepository.findById((long) catId).orElseThrow(() -> {
            throw new CategoryCastomException(String.format("Такой категории не сущетствует", catId));
        });
        return categoryMapper.mapToCategoryDto(category);
    }
}
