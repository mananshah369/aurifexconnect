package com.erp.Exception.Customer.ExceptionHandler;

import com.erp.Exception.Customer.CustomerNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> customerNotFoundHandler(CustomerNotFoundException e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
