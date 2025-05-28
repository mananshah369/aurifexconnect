package com.erp.Exception.BankAccount;

import lombok.Getter;

@Getter
public class InactiveBankAccountException extends RuntimeException {

    private String message;

    public InactiveBankAccountException(String message) {
        this.message = message;
    }
}
