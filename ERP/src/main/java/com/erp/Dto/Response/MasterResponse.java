package com.erp.Dto.Response;

import com.erp.Enum.ReferenceType;
import com.erp.Enum.TransactionStatus;
import com.erp.Enum.VoucherType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MasterResponse {

    private long masterId;

    private String name;

    private VoucherType voucherType;

    private double amount;

    private ReferenceType referenceType;

    private TransactionStatus transactionStatus;

    private String description;

    private String voucherIndex;

    private LocalDateTime date;

}
