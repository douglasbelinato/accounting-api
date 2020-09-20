package com.accounting.accountingapi.exception.dto;

public class ErrorResponseDTO {

    private String userMessage;
    private String traceMessage;

    public ErrorResponseDTO(String userMessage, String traceMessage) {
        this.userMessage = userMessage;
        this.traceMessage = traceMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getTraceMessage() {
        return traceMessage;
    }
}
