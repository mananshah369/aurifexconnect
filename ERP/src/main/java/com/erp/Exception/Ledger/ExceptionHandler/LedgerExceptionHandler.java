package com.erp.Exception.Ledger.ExceptionHandler;

import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LedgerExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> LedgerNotFoundHandler(LedgerNotFoundException e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
