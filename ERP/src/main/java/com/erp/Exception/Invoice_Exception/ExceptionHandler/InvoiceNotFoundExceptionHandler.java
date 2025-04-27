package com.erp.Exception.Invoice_Exception.ExceptionHandler;
import com.erp.Exception.Invoice_Exception.InvoiceNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class InvoiceNotFoundExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> inventoryNotFoundHandler(InvoiceNotFoundException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
