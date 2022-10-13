package ru.practicum.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.category.CategoryDto;
import ru.practicum.app.category.CategoryService;
import ru.practicum.app.category.NewCategoryDto;
import ru.practicum.app.compilation.CompilationDto;
import ru.practicum.app.compilation.CompilationService;
import ru.practicum.app.compilation.NewCompilationDto;
import ru.practicum.app.event.*;
import ru.practicum.app.exception.EntryNotFoundException;
import ru.practicum.app.user.NewUserRequest;
import ru.practicum.app.user.UserDto;
import ru.practicum.app.user.UserService;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {

    private final UserService userService;

    private final EventService eventService;

    private final CompilationService compilationService;

    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService,
                           CompilationService compilationService,
                           EventService eventService,
                           UserService userService) {
        this.categoryService = categoryService;
        this.compilationService = compilationService;
        this.eventService = eventService;
        this.userService = userService;
    }


    /***
     * Работа с категория от имени Администратора
     * - cоздание новой категории
     * - обновление категории
     * - удаление категорий
     ***/

    @PostMapping(value = "/categories")
    public CategoryDto create(@RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.create(newCategoryDto);
    }

    @PatchMapping(value = "/categories")
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @DeleteMapping(value = "/categories/{catId}")
    public void delete(@PathVariable(value = "catId") @NotNull int catId) {
        categoryService.delete(catId);
    }

    /***
     * Работа с Подборками от имени Администратора
     * - cоздание новой подборки
     * - удаление подборки
     * - добавление события в подборку
     * - удаление события из подборки
     * - закрепить подборку
     * - открепить подборку
     ***/


    @PostMapping("/compilations")
    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilationById(@PathVariable Integer compId) {
        compilationService.deleteCompilationById(compId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Integer compId,
                                      @PathVariable Integer eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Integer compId,
                                           @PathVariable Integer eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable Integer compId) {
        compilationService.pinCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable Integer compId) {
        compilationService.unpinCompilation(compId);
    }

    /*** Admin: События. API для работы с событиями
     * - поиск событий
     * - редактирование события
     * - публикация события
     * - отклонить событие
     */

    @PutMapping(value = "/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable int eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.trace("Событие {} добавлено", adminUpdateEventRequest.getAnnotation());
        return eventService.updateEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping(value = "/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable int eventId) {
        return eventService.publishEvent(eventId);
    }

    @PatchMapping(value = "/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable int eventId) {
        return eventService.rejectEvent(eventId);
    }

    @GetMapping(value = "/events")
    public Collection<EventFullDto> getEventByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return eventService.getEventByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /***
     *  Admin: Пользователи. API для работы с пользователями
     *  - добавление нового польщователя
     *  - получение списка пользователей
     *  - удаление пользователя
     */

    @PostMapping(value = "/users")
    public UserDto create(@RequestBody NewUserRequest newUserRequest) {
        return userService.create(newUserRequest);
    }


    @GetMapping(value = "/users")
    public List<UserDto> getUser(@RequestParam(required = false) List<Integer> ids,
                                 @RequestParam(defaultValue = "0") Integer from,
                                 @RequestParam(defaultValue = "10") Integer size) {
        return userService.getAllUsers(ids, from, size);
    }

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable @NotNull int userId) throws EntryNotFoundException {
        userService.delete(userId);
    }

}
