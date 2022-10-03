package ru.practicum.app.service;



import ru.practicum.app.dto.StatInputDto;
import ru.practicum.app.dto.StatOutputDto;

import java.util.List;

public interface StatService {
    void saveStat(StatInputDto statInputDto);

    List<StatOutputDto> getStat(String start, String  end, String[] uris, boolean isUnique);
}
