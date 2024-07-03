package com.inson.ersp.commons.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
public class NotCreatException extends RuntimeException{
    public NotCreatException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCreatException(String message) {super(message);}

    public NotCreatException(Throwable cause) { super(cause); }
}
