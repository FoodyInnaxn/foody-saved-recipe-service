package com.foody.savedrecipeservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyAddedException extends ResponseStatusException {
    public AlreadyAddedException() {
        super(HttpStatus.FORBIDDEN, "ALREADY_ADDED");
    }
}
