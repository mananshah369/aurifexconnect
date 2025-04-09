package com.erp.Exception.ServiceType.ExceptionHandler;

import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceTypeExceptionHandler {
    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> serviceTypeNotFoundHandler(ServiceTypeNotFoundByIdException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
