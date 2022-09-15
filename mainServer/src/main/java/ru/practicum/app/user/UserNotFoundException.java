package ru.practicum.app.user;

import java.sql.SQLException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
