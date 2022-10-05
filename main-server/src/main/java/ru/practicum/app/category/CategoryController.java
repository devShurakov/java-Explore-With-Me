package ru.practicum.app.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping
@Slf4j
@Validated
public class CategoryController {


    private final CategoryServiceImpl categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/admin/categories")
    public CategoryDto create(@RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.create(newCategoryDto);
    }

    @PatchMapping(value = "/admin/categories")
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @DeleteMapping(value = "/admin/categories/{catId}")
    public void delete(@PathVariable(value = "catId") @NotNull int catId) {
         categoryService.delete(catId);
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping(value = "/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable("catId") @NotNull int catId) {
        return categoryService.getCategoryById(catId);
    }
}
