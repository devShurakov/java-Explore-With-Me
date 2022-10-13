package ru.practicum.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.config.ConfigProperties;
import ru.practicum.app.category.CategoryDto;
import ru.practicum.app.category.CategoryService;
import ru.practicum.app.compilation.CompilationDto;
import ru.practicum.app.compilation.CompilationService;
import ru.practicum.app.event.*;
import ru.practicum.app.statistic.EndpointHit;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class PublicController {

    URI uri;
    HttpClient client = HttpClient.newHttpClient();

    private final EventService eventService;

    private final CompilationService compilationService;

    private final CategoryService categoryService;

    @Autowired
    public PublicController(CategoryService categoryService,
                            CompilationService compilationService,
                            EventService eventService,
                            ConfigProperties configProperties) {
        this.categoryService = categoryService;
        this.compilationService = compilationService;
        this.eventService = eventService;
        this.uri = URI.create(configProperties.getStatistic() + "/hit");
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
    public EventFullDto getFullInfoEvents(@PathVariable(value = "eventId") int eventId, HttpServletRequest request) {
        sendStatistic(request);
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
        sendStatistic(request);
        return eventService.getFilteredEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    private void sendStatistic(HttpServletRequest request) {
        try {
            HttpRequest statRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Accept", "text/html")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(EndpointHit.builder().app("explore").timestamp(LocalDateTime.now()).uri(request.getRequestURI()).ip(request.getRemoteAddr()).build().toString()))
                    .build();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            client.send(statRequest, handler);
        } catch (IOException | InterruptedException ex) {
            log.error("Соединение с сервером статистики потеряно. Невозможно отправить данные.");
        }
    }

}
