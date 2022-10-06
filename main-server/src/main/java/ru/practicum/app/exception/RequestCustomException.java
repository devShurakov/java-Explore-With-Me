package ru.practicum.app.exception;

public class RequestCustomException extends RuntimeException {
    public RequestCustomException(String message) {
        super(message);
    }
}

