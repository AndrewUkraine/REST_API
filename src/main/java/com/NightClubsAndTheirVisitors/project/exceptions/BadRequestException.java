package com.NightClubsAndTheirVisitors.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String exceptionMessage, Long id) {
        super(exceptionMessage + " with ID: " + id + " not found");
    }

    public BadRequestException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public BadRequestException(Long id, String exceptionMessage) {
        super("Book id not found : " + id);
    }

}
