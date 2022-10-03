package ru.practicum.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatOutputDto {
    private String app;
    private String uri;
    private int hits;
}
