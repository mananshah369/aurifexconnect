package com.erp.Dto.Request;

import com.erp.Enum.VoucherType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MasterRequest {

    private String name;

    private VoucherType voucherType;

    private double amount;

    private String description;

    private String voucherIndex;

    //optional filed
    private long bankAccountId;
    private long invoiceId;
    private long billId;
    private long ledgerId;
    private long findMasterId;

 //voucher find based on voucher type and increment there index

}
