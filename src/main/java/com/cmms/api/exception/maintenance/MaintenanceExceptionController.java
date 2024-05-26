package com.cmms.api.exception.maintenance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MaintenanceExceptionController {

    @ExceptionHandler(MaintenanceAlreadyExistsException.class)
    public ResponseEntity<String> handleMaintenanceAlreadyExistsException(MaintenanceAlreadyExistsException ex) {
        return new ResponseEntity<>("No EOS devices detected", HttpStatus.NO_CONTENT);
    }

}
