package com.accounting.accountingapi.infrastructure.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageSourceComponentTest {

    @Autowired
    public MessageSourceComponent messageSourceComponent;

    @MockBean
    private MessageSource messageSource;

    @Test
    public void shouldGetAMessageByMessageKey() {
        String message = "message";
        when(messageSource.getMessage(any(), any(), any())).thenReturn(message);

        String messageObtained = messageSourceComponent.getMessage("message.key", null);

        Assertions.assertThat(messageObtained).isEqualTo(message);
    }

    @Test
    public void shouldGetAMessageByFieldError() {
        String message = "message";
        FieldError fieldError = new FieldError("Person", "name", "empty field");
        when(messageSource.getMessage(any(), any())).thenReturn(message);

        String messageObtained = messageSourceComponent.getMessage(fieldError);

        Assertions.assertThat(messageObtained).isEqualTo(message);
    }
}
