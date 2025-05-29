package com.erp.Service.Voucher;

import com.erp.Dto.Response.VoucherResponse;
import com.erp.Enum.VoucherType;
import com.erp.Model.Voucher;

public interface VoucherService {

    Voucher generateFormattedVoucherId(VoucherType type);

    VoucherResponse findById(long id);

    String getFormattedVoucherId(Voucher voucher);
}
