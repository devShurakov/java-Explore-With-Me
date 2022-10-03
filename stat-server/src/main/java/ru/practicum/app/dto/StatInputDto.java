package ru.practicum.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StatInputDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
