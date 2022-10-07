package ru.practicum.app.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.app.event.Event;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RequestDto {

    private Integer id;

    private Event event;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    private Integer requester;


}