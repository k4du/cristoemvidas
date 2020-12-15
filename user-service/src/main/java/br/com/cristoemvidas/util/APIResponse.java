package br.com.cristoemvidas.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    public APIResponse() {

    }

    public static APIResponse buildToSuccess(String message, Object content) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.message = message;
        apiResponse.content = content;

        return apiResponse;
    }

    public static APIResponse buildToError(Exception e) {

        APIResponse apiResponse = new APIResponse();
        apiResponse.error = e.getMessage();

        if (e instanceof GenericProcessException) {

            GenericProcessException procEx = (GenericProcessException) e;

            apiResponse.errorDescription = procEx.getFullDescription();
            apiResponse.status = procEx.getStatus();
            apiResponse.errorDetailList = procEx.getErrorDetailList();
            apiResponse.code = procEx.getStatus().getHttpCode();

        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = (ConstraintViolationException) e;
            apiResponse.errorDetailList = violationException.getConstraintViolations().stream()
                    .map(constraintViolation -> ErrorDetail.builder().message(constraintViolation.getMessage()).build())
                    .collect(Collectors.toList());
            apiResponse.code = Status.UNPROCESSABLE_ENTITY.getHttpCode();
            apiResponse.status = Status.UNPROCESSABLE_ENTITY;

        } else {

            apiResponse.status = Status.RUNTIME_ERROR;

        }


        return apiResponse;
    }

    public static APIResponse buildToNotAuthorized() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.error = "session.invalid";
        apiResponse.status = Status.NOT_AUTHORIZED;
        apiResponse.code = apiResponse.status.getHttpCode();
        return apiResponse;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.code = status.getHttpCode();
    }

    public Status getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<ErrorDetail> getErrorDetailList() {
        return errorDetailList;
    }

    public void setErrorDetailList(List<ErrorDetail> errorDetailList) {
        this.errorDetailList = errorDetailList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    private Status status = Status.SUCCESS;
    /**
     * Código de retorno http
     */
    private Integer code = Status.SUCCESS.getHttpCode();
    /**
     * O erro HTTP que ocorreu, por exemplo. Bad Request
     */
    private String error;
    /**
     * A descrição do motivo da causa do erro.
     * Ex: The Contract 'http://api.gpa.digital/pa/user' does not exist
     */
    private String errorDescription;
    /**
     * Lista de detalhes do resultado da validação para orientar a view
     */
    private List<ErrorDetail> errorDetailList;
    /**
     * Mensagem amigável de negócio referente a esse retorno
     */
    private String message;
    /**
     * Objeto principal de conteúdo
     */
    private Object content;

    public static enum Status {
        SUCCESS("success", 200),
        RUNTIME_ERROR("runtime_error", 500),
        BAD_REQUEST("bad_request", 400),
        NOT_FOUND("not_found", 404),
        NOT_AUTHORIZED("forbidden", 403),
        UNPROCESSABLE_ENTITY("Unprocessable Entity" , 422);

        Status(String code, Integer httpCode) {
            this.code = code;
            this.httpCode = httpCode;
        }

        @JsonCreator
        public static Status fromValue(String typeCode) {
            for (Status c : Status.values()) {
                if (c.code.equals(typeCode)) {
                    return c;
                }

            }

            throw new IllegalArgumentException("Status inválido: " + typeCode);

        }

        @JsonValue
        public String getCode() {
            return code;
        }

        public Integer getHttpCode() {
            return httpCode;
        }

        private String code;
        private Integer httpCode;
    }
}
