package ru.practicum.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.app.dto.StatInputDto;
import ru.practicum.app.dto.StatOutputDto;
import ru.practicum.app.service.StatService;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
public class StatisticsController {

    private final StatService statService;

    @Autowired
    public StatisticsController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping("/hit")
    public void saveStat(@RequestBody StatInputDto statInputDto) {
        log.info("Новая запись в статистику: {}", statInputDto);
        statService.saveStat(statInputDto);
    }

    @GetMapping("/stats")
    public List<StatOutputDto> getStat(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam String[] uris,
                                       @RequestParam(defaultValue = "false") boolean unique) {
        for (int i = 0; i < uris.length; i++) {
            uris[i] = URLDecoder.decode(uris[i], StandardCharsets.UTF_8);
        }
        log.info("Запрошена статистика с {} по {} uri: {} уникальность {}",
                URLDecoder.decode(start, StandardCharsets.UTF_8),
                URLDecoder.decode(end, StandardCharsets.UTF_8),
                uris,
                unique);
        return statService.getStat(URLDecoder.decode(start, StandardCharsets.UTF_8),
                                   URLDecoder.decode(end, StandardCharsets.UTF_8),
                                   uris,
                                   unique);
    }
}