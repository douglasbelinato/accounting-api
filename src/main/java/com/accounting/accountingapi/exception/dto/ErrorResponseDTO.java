package com.accounting.accountingapi.exception.dto;

import java.util.Arrays;
import java.util.List;

public class ErrorResponseDTO {

    private List<String> messages;

    public ErrorResponseDTO(String... messages) {
        this.messages = Arrays.asList(messages);
    }

    public ErrorResponseDTO(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
