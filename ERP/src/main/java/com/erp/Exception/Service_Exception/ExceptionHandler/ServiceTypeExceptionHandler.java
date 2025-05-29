package com.erp.Exception.Service_Exception.ExceptionHandler;

import com.erp.Exception.Service_Exception.ServiceTypeNotFoundByIdException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceTypeExceptionHandler {
    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> serviceTypeNotFoundHandler(ServiceTypeNotFoundByIdException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
