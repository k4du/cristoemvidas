package br.com.cristoemvidas.util;

import java.util.List;

public class GenericProcessException extends RuntimeException {
    public GenericProcessException() {

    }

    public GenericProcessException(String message) {
        super(message);
        this.status = APIResponse.Status.RUNTIME_ERROR;
    }

    public GenericProcessException(String message, APIResponse.Status responseStatus) {
        super(message);
        this.status = responseStatus;
    }

    public GenericProcessException(String message, APIResponse.Status responseStatus, List<ErrorDetail> errorDetailList) {
        super(message);
        this.status = responseStatus;
        this.errorDetailList = errorDetailList;
    }

    public APIResponse.Status getStatus() {
        return status;
    }

    public void setStatus(APIResponse.Status status) {
        this.status = status;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public List<ErrorDetail> getErrorDetailList() {
        return errorDetailList;
    }

    public void setErrorDetailList(List<ErrorDetail> errorDetailList) {
        this.errorDetailList = errorDetailList;
    }

    private APIResponse.Status status;
    private String fullDescription;
    private List<ErrorDetail> errorDetailList;
}
