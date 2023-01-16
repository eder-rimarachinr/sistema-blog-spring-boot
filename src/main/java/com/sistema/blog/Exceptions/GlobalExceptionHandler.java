package com.sistema.blog.Exceptions;

import com.sistema.blog.Models.DTO.DetailsError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetailsError> ManejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<DetailsError> ManejarBlogAppException(BlogAppException exception, WebRequest webRequest) {
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetailsError> ManejarGlobalException(Exception exception, WebRequest webRequest) {
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nameField = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(nameField, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
