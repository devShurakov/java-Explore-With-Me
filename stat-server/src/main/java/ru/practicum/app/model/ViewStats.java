package ru.practicum.app.model;

import lombok.*;

import javax.persistence.*;

/***
 * запись информации о том, что был обработан запрос к эндпоинту API;
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;
}
