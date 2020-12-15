package br.com.cristoemvidas.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {
    public static ErrorDetail buildToPaymentForm(String code, String message, Long sequence) {
        ErrorDetail ed = ErrorDetail.builder()
        .code(code)
        .message(message)
        .entity("paymentForm")
        .entitySequence(sequence).build();

        return ed;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntitySequence() {
        return entitySequence;
    }

    public void setEntitySequence(Long entitySequence) {
        this.entitySequence = entitySequence;
    }

    private String code;
    private String message;
    private String entity;
    private Long entityId;
    private Long entitySequence;
}

