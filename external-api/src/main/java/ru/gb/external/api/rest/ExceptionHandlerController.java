package ru.gb.external.api.rest;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> feignHandler(FeignException e) {
        if (e.status() == 409) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else throw e;
    }
}