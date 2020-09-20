package com.accounting.accountingapi.exception.handler;

import com.accounting.accountingapi.exception.NotFoundException;
import com.accounting.accountingapi.exception.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AccountingApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity.HeadersBuilder<?> handleNotFoundException() {
        return ResponseEntity.notFound();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        String propertyName;
        String message = "";

        if (ex.getRootCause() instanceof UnrecognizedPropertyException) {
            propertyName = ((UnrecognizedPropertyException) ex.getRootCause()).getPropertyName();
            message = messageSource.getMessage("api.error.request.json.unrecognized.field", new String[]{propertyName},
                    LocaleContextHolder.getLocale());
        }

        return handleExceptionInternal(ex, new ErrorResponseDTO(message), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ErrorResponseDTO(messages), headers, HttpStatus.BAD_REQUEST, request);
    }
}
