package com.erp.Exception.Master;

public class MasterNotFoundException extends RuntimeException {

  private String message;

    public MasterNotFoundException(String message) {
      this.message = message;
    }
}
