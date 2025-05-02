package com.erp.Exception.BillLine.ExceptionHandler;

import com.erp.Exception.BillLine.BillLineNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BillLineExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> billLineNotFoundHandler(BillLineNotFoundException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
