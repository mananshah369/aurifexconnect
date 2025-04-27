package com.erp.Exception.Inventory_Exception.ExceptionHendler;

import com.erp.Exception.Inventory_Exception.WrongQuantityNumberException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WrongQuantityNumberExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> WrongQuantityNumberExceptionHandler(WrongQuantityNumberException e) {
        return ResponseBuilder.error(HttpStatus.BAD_REQUEST,e.getMessage());
    }
}
