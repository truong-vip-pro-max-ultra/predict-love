package com.truongjae.predictlove.controller;

import com.truongjae.predictlove.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<APIError> objectNotFoundException(ObjectNotFoundException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<APIError> badRequestException(BadRequestException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OKException.class)
    public final ResponseEntity<APIError> OKException(OKException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.OK);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<APIError> UnauthorizedException(UnauthorizedException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

}
