package br.com.infnet.academicsystemapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Anotação para que Spring retorne status HTTP 400
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
