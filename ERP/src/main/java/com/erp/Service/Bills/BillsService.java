package com.erp.Service.Bills;

import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Response.BillsResponse;

import java.util.List;

public interface BillsService {
    BillsResponse addBill(BillsRequest billsRequest, long ledgerId);

    BillsResponse updateBill(BillsRequest billsRequest, long billId, long ledgerId);

    BillsResponse findByBillId(long billId);

    BillsResponse deleteByBillId(long billId);

    List<BillsResponse> findBillBySupplierName(String legerName);
}
