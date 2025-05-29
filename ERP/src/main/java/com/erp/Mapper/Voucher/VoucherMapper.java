package com.erp.Mapper.Voucher;

import com.erp.Dto.Response.VoucherResponse;
import com.erp.Model.Voucher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    VoucherResponse mapToVoucherResponse(Voucher voucher);

    List<VoucherResponse> mapToVoucherResponse(List<Voucher> vouchers);
}
