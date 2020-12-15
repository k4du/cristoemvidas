package br.com.cristoemvidas.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GeneralAPIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptionAPI(HttpServletResponse response, Exception e, WebRequest request) {

        APIResponse responseMessage = APIResponse.buildToError(e);

        response.setStatus(responseMessage.getCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //TODO validar com o front se eles estao usando o status conde 422 pois deveria ter uma validacao se retornou
        //TODO status code do response e tratar
        return handleExceptionInternal(e, responseMessage, headers, HttpStatus.valueOf(response.getStatus()), request);

    }

}
