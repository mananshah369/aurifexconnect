package com.erp.Exception.Bills.ExceptionHandler;

import com.erp.Exception.Bills.BillNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BillsExceptionHandler {
    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> billNotFoundHandler(BillNotFoundException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
