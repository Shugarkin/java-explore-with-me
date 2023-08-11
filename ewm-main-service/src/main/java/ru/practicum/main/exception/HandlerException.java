package ru.practicum.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice("ru.practicum")
public class HandlerException {

//    @ExceptionHandler({MissingRequestHeaderException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    public ErrorResponse userError(final Exception e) {
//
//        return new ErrorResponse(e.getMessage());
//    }
}
