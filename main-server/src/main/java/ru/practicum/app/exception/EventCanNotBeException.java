package ru.practicum.app.exception;

public class EventCanNotBeException extends RuntimeException {
    public EventCanNotBeException(String message) {
        super(message);
    }

}
