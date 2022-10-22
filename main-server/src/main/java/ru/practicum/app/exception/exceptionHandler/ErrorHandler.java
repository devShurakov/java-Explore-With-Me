package ru.practicum.app.exception.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.app.exception.*;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException e) {
        Response response = new Response(e.getMessage());
        log.trace("Ошибка MethodArgumentNotValidException");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleException2(ConstraintViolationException e) {
        Response response = new Response(e.getMessage());
        log.trace("Ошибка ConstraintViolationException");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(CategoryCastomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(CategoryCastomException exception) {
        log.trace("Ошибка CategoryCastomException");
        return new Response(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ResponseBody
    @ExceptionHandler(UserCastomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(UserCastomException exception) {
        log.trace("Ошибка UserCastomException");
        return new Response(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ResponseBody
    @ExceptionHandler(UserCastomExceptionTwo.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(UserCastomExceptionTwo exception) {
        log.trace("Ошибка UserCastomException");
        return new Response(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ResponseBody
    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(EntryNotFoundException exception) {
        log.trace("Ошибка UserCastomException");
        return new Response(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(ValidationException exception) {
        log.trace("Ошибка UserCastomException");
        return new Response(
                "Only pending or canceled events can be changed",
                "For the requested operation the conditions are not met.",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));

    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response categoryNotFound(CategoryNotFoundException exception) {
        log.trace("Ошибка CategoryCastomException");
        return new Response(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }


}