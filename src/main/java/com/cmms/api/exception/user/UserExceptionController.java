package com.cmms.api.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Object> handleFilmNotFoundException(EmptyFieldException exception) {
        return new ResponseEntity<>("Empty username or password", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<Object> handleUsernameExistException(UsernameExistException exception) {
        return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<Object> handleEmailExistException(EmailExistException exception) {
        return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
    }

}
