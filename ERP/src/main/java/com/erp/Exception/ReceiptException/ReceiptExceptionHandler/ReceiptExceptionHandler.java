package com.erp.Exception.ReceiptException.ReceiptExceptionHandler;

import com.erp.Exception.ReceiptException.ReceiptNotFound;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReceiptExceptionHandler {
    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> ReceiptNotFoundHandler(ReceiptNotFound e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
