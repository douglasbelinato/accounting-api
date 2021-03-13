package com.accounting.accountingapi.infrastructure.exception.handler;

import com.accounting.accountingapi.infrastructure.exception.EmptyBodyException;
import com.accounting.accountingapi.infrastructure.exception.ResourceNotFoundException;
import com.accounting.accountingapi.infrastructure.exception.dto.ErrorResponseDTO;
import com.accounting.accountingapi.infrastructure.message.MessageSourceComponent;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AccountingApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSourceComponent messageSourceComponent;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyBodyException.class)
    public ResponseEntity<Object> handleEmptyBodyException() {
        return new ResponseEntity<>(new ErrorResponseDTO(createMessageEmptyBody()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ErrorResponseDTO("Erro"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        var message = new StringBuilder();

        if (ex.getCause() instanceof UnrecognizedPropertyException) {
            message.append(createMessageUnrecognizedProperty((UnrecognizedPropertyException) ex.getCause()));
        } else {
            message.append("Illegal Argument Exception");
        }

        return new ResponseEntity<>(new ErrorResponseDTO(message.toString()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        var message = new StringBuilder();

        if (ex.getRootCause() instanceof UnrecognizedPropertyException) {
            message.append(createMessageUnrecognizedProperty((UnrecognizedPropertyException) ex.getRootCause()));
        } else {
            message.append(createMessageEmptyBody());
        }

        return handleExceptionInternal(ex, new ErrorResponseDTO(message.toString()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> messageSourceComponent.getMessage(fieldError))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ErrorResponseDTO(messages), headers, HttpStatus.BAD_REQUEST, request);
    }

    private String createMessageUnrecognizedProperty(UnrecognizedPropertyException ex) {
        return messageSourceComponent.getMessage("api.error.request.json.unrecognized.field", ex.getPropertyName());
    }

    private String createMessageEmptyBody() {
        return messageSourceComponent.getMessage("api.error.request.empty.body");
    }
}
