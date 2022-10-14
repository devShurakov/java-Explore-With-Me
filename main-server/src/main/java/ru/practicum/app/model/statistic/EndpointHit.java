package ru.practicum.app.model.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
public class EndpointHit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{\"app\": \"" + this.app + "\",\n" +
                "  \"uri\": \"" + this.uri + "\",\n" +
                "  \"ip\": \"" + this.ip + "\",\n" +
                "  \"timestamp\": \"" + this.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\"}";
    }
}
