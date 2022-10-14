package ru.practicum.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    Integer event;

    Integer requester;

    String status;

}
