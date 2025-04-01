package com.erp.Exception.Inventory;

import lombok.Getter;

@Getter
public class InventoryNotFoundException extends RuntimeException {

    private final String message ;
    public InventoryNotFoundException(String message) {
        this.message = message;
    }
}
