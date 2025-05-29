package com.erp.Dto.Response;

import com.erp.Enum.VoucherType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherResponse {

    private long voucherId;

    private VoucherType voucherType;

    private String voucherIndex;
}
