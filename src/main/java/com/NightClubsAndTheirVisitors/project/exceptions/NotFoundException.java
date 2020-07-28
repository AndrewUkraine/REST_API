package com.NightClubsAndTheirVisitors.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String exceptionMessage, Long id) {
        super(exceptionMessage + " with ID: " + id + " not found");
    }

    public NotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public NotFoundException(Long id,  String exceptionMessage) {
        super("Book id not found : " + id);
    }

}
