package com.erp.Exception.BankAccount;

public class BankAccountNotFoundException extends RuntimeException {
    private String message;
    public BankAccountNotFoundException(String message) {
        this.message = message;
    }
}
