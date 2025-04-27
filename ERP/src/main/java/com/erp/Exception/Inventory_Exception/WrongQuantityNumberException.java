package com.erp.Exception.Inventory_Exception;

import lombok.Getter;

@Getter
public class WrongQuantityNumberException extends RuntimeException {
    private final String message;

    public WrongQuantityNumberException(String message) {
        this.message = message;
    }
}
