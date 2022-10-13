package ru.practicum.app.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.model.EndpointHit;
import ru.practicum.app.model.ViewStats;
import ru.practicum.app.service.StatisticService;

import java.util.Collection;
import java.util.List;

@RequestMapping
@RestController
@Slf4j
public class StatisticsController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @PostMapping(value = "/hit")
    public void addHit(@RequestBody EndpointHit endpointHit) {
        log.info("Сохранение информации о том, что к эндпоинту был запрос");
        statisticService.addHit(endpointHit);
    }

    @GetMapping(value = "/stats")
    public Collection<ViewStats> getStatistics(@RequestParam String start,
                                               @RequestParam String end,
                                               @RequestParam(required = false) List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получение статистики по посещениям");
        return statisticService.getStatistic(start, end, uris, unique);
    }
}
