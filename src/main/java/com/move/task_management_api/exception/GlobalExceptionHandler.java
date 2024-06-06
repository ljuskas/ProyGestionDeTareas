package com.move.task_management_api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.move.task_management_api.dto.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String nombreCampo = error.getField();
            String mensajeError = error.getDefaultMessage();
            errors.put(nombreCampo.toUpperCase(), mensajeError);
        });

        String errorMessage = messageSource.getMessage("error.validation", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("validationError", errorMessage, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Mensaje", ex.getLocalizedMessage());
        String errorMessage = messageSource.getMessage("error.internal", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("generic_error", errorMessage, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomExceptions.CustomNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(CustomExceptions.CustomNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Mensaje", ex.getLocalizedMessage());
        String errorMessage = messageSource.getMessage("error.not_found", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("not_found", errorMessage, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExceptions.CustomUnauthorizedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationException(CustomExceptions.CustomUnauthorizedException ex, WebRequest request) {
        String errorMessage = messageSource.getMessage("error.unauthorized", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("unauthorized", errorMessage, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomExceptions.CustomBadRequestException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequestException(CustomExceptions.CustomBadRequestException ex, WebRequest request) {
        String errorMessage = messageSource.getMessage("error.bad_request", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("bad_request", errorMessage, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.CustomAccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException(CustomExceptions.CustomAccessDeniedException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Mensaje", ex.getLocalizedMessage());
        String errorMessage = messageSource.getMessage("error.forbidden", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = new ErrorResponse("access_denied", errorMessage, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomExceptions.CustomUserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(CustomExceptions.CustomUserAlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("user_already_exists", ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
