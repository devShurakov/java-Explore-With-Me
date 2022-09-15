package ru.practicum.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.app.user.UserController;
import ru.practicum.app.user.UserNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleException2(ConstraintViolationException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException3(UserNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> dataIsNotValid(final MethodArgumentNotValidException e) {
//        return Map.of("error", "invalid user data");
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Map<String, String> dataIsNotValid(final ConstraintViolationException e) {
//        return Map.of("error", "invalid item data");
//    }

}