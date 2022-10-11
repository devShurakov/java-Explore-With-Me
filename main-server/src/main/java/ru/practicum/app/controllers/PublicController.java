package ru.practicum.app.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.category.CategoryDto;
import ru.practicum.app.category.CategoryServiceImpl;
import ru.practicum.app.compilation.CompilationDto;
import ru.practicum.app.compilation.CompilationServiceImpl;
import ru.practicum.app.event.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class PublicController {

    private final EventServiceImpl eventService;

    private final CompilationServiceImpl compilationService;

    private final CategoryServiceImpl categoryService;

    @Autowired
    public PublicController(CategoryServiceImpl categoryService,
                            CompilationServiceImpl compilationService,
                            EventServiceImpl eventService) {
        this.categoryService = categoryService;
        this.compilationService = compilationService;
        this.eventService = eventService;
    }

    /***
     * Публичная работа с Категориями
     * - получение категории
     * - получение категории по Id
     ***/

    @GetMapping(value = "/categories")
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping(value = "/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable("catId") int catId) {
        return categoryService.getCategoryById(catId);
    }

    /***
     * Публичная работа с Подборками
     * - получение коллекции подборок
     * - получение подборки по id
     ***/

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable Integer compId) {
        return compilationService.getCompilationById(compId);
    }

    /***
     *  - Публичный API для работы с событиями:
     *  - Получение событий с возможностью фильтрации
     *  - Получение подробной информации об опубликованном событии по его идентификатору
     *  - редактирование события
     */

    @GetMapping(value = "/events/{eventId}")
    public EventFullDto getFullInfoEvents(@PathVariable(value = "eventId") int eventId) {
        return eventService.getFullInfoEvents(eventId);
    }

    @GetMapping(value = "/events")
    public Collection<EventShortDto> getFilteredEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventSortValues sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
//        sendStatistic(request);
        return eventService.getFilteredEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

}
