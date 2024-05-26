package com.cmms.api.exception.maintenance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MaintenanceExceptionController {

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Object> handleFilmNotFoundException(InvalidDateException exception) {
        return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
    }

}
