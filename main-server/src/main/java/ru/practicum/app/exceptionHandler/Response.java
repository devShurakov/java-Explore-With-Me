package ru.practicum.app.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private String reason;
    private String status;
    private String timestamp;

    public Response(String message) {
        this.message = message;
    }
}
