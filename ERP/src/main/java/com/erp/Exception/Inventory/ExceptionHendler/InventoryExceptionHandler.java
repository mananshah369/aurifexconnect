package com.erp.Exception.Inventory.ExceptionHendler;

import com.erp.Exception.Inventory.InventoryNotFoundException;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> inventoryNotFoundHandler(InventoryNotFoundException e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
