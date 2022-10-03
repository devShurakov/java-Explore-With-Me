package ru.practicum.app.mapper;



import ru.practicum.app.dto.StatInputDto;
import ru.practicum.app.dto.StatOutputDto;
import ru.practicum.app.model.StatHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatMapper {
    public static StatHit dtoToStatHit(StatInputDto statInputDto) {
        StatHit statHit = new StatHit();
        statHit.setApp(statInputDto.getApp());
        statHit.setIp(statInputDto.getIp());
        statHit.setUri(statInputDto.getUri());
        statHit.setTimestamp(LocalDateTime.parse(statInputDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return statHit;
    }

    public static StatOutputDto statHitToDto(List<StatHit> statHit) {
        if (statHit.isEmpty()) {
            return new StatOutputDto();
        }
        return new StatOutputDto(
                statHit.get(0).getApp(),
                statHit.get(0).getUri(),
                statHit.size()
        );
    }
}
