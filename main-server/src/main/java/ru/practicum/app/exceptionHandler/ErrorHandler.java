package ru.practicum.app.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.app.exception.CategoryCastomException;
import ru.practicum.app.exception.EntryNotFoundException;
import ru.practicum.app.exception.UserCastomException;
import ru.practicum.app.exception.UserCastomException2;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

//    @ExceptionHandler(UserCastomException.class)
//    public ResponseEntity<Response> handleException3(UserCastomException e) {
//        Response response = new Response(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @ExceptionHandler(UserCastomException2.class)
//    public ResponseEntity<Response> handleException3(UserCastomException2 e) {
//        Response response = new Response(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(CategoryCastomException.class)
//    public ResponseEntity<Response> handleException3(CategoryCastomException e) {
//        Response response = new Response(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ResponseBody
    @ExceptionHandler(CategoryCastomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(CategoryCastomException exception) {
//        log.error(exception.getMessage());
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
//        log.error(exception.getMessage());
        return new Response(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ResponseBody
    @ExceptionHandler(UserCastomException2.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response onValidationException(UserCastomException2 exception) {
//        log.error(exception.getMessage());
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
//        log.error(exception.getMessage());
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
//        log.error(exception.getMessage());
        return new Response(
                "Only pending or canceled events can be changed",
                "For the requested operation the conditions are not met.",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));

//        "message": "Only pending or canceled events can be changed",
//                "reason": "For the requested operation the conditions are not met.",
//                "status": "400 BAD_REQUEST",
//                "timestamp": "yyyy-MM-dd HH:mm:ss"

    }



}