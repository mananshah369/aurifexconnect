package com.erp.Exception.Bills;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BillNotFoundException extends RuntimeException {
   private String message;
}
