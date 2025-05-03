package com.erp.Exception.Customer;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {

  private final String message;
  public CustomerNotFoundException(String message){
    this.message = message;
  }
}
