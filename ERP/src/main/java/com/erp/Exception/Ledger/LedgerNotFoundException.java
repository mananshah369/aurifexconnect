package com.erp.Exception.Ledger;

import lombok.Getter;

@Getter
public class LedgerNotFoundException extends RuntimeException {

  private final String message;
  public LedgerNotFoundException(String message){
    this.message = message;
  }
}
