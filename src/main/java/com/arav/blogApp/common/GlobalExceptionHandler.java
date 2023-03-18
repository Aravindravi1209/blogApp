package com.arav.blogApp.common;

import com.arav.blogApp.exceptions.AccessDeniedException;
import com.arav.blogApp.exceptions.BadRequestException;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequest(BadRequestException e)
    {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDenied(AccessDeniedException e)
    {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity handleException(PropertyValueException e)
    {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleException(DataIntegrityViolationException e)
    {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleException(HttpMessageNotReadableException e)
    {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
