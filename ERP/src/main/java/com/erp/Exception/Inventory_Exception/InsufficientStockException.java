package com.erp.Exception.Inventory_Exception;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {

    private final String message ;

    public InsufficientStockException(String message) {
        this.message = message;
    }
}
