package com.accounting.accountingapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageKey, String... params) {
        return messageSource.getMessage(messageKey, params, LocaleContextHolder.getLocale());
    }

    public String getMessage(FieldError fieldError) {
        return messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    }
}
