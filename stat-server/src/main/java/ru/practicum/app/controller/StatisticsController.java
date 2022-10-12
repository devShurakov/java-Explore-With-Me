package ru.practicum.app.controller;


import org.springframework.web.bind.annotation.*;
import ru.practicum.app.model.EndpointHit;
import ru.practicum.app.model.ViewStats;
import ru.practicum.app.service.StatisticService;

import java.util.Collection;
import java.util.List;

@RestController
public class StatisticsController {
    private final StatisticService statisticService;

    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @PostMapping(path = "/hit")
    public EndpointHit addHit(@RequestBody EndpointHit endpointHit) {
        return statisticService.addHit(endpointHit);
    }

    @GetMapping(path = "/stats")
    public Collection<ViewStats> getStatistics(@RequestParam String start,
                                               @RequestParam String end,
                                               @RequestParam(required = false) List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        return statisticService.getStatistic(start, end, uris, unique);
    }
}
