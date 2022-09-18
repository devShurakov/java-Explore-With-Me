package ru.practicum.app.request;

public class RequestCustomException extends RuntimeException {
    public RequestCustomException(String message) {
        super(message);
    }
}

