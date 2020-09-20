package com.accounting.accountingapi.exception.handler;

import com.accounting.accountingapi.exception.NotFoundException;
import com.accounting.accountingapi.exception.dto.ErrorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        String userMessage = messageSource.getMessage("api.error.invalid.request", null, LocaleContextHolder.getLocale());
        String traceMessage = ex.getCause().getMessage();

        return handleExceptionInternal(ex, new ErrorResponseDTO(userMessage, traceMessage), headers, HttpStatus.BAD_REQUEST, request);
    }
}
