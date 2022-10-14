package ru.practicum.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.app.model.request.RequestStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private Integer id;
    @NotNull
    private Integer event;
    @NotNull
    private Integer requester;
    private RequestStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

}