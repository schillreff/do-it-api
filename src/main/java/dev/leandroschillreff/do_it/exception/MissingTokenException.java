package dev.leandroschillreff.do_it.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MissingTokenException extends RuntimeException {
    public MissingTokenException(String message) {
        super(message);
    }
}