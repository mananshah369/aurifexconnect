package com.erp.Service.BillLine;

import com.erp.Dto.Request.BillLineRequest;
import com.erp.Dto.Response.BillLineResponse;

import java.util.List;

public interface BillLineService {
    BillLineResponse addBillLineItems(BillLineRequest billLineRequest, long billId, long itemId);

    BillLineResponse updateBillLineItemById(BillLineRequest billLineRequest, long billLineId, long billId, long itemId);

    List<BillLineResponse> getBillLinesByDescription(String description);

    BillLineResponse deleteBillLineById(long billLineId);

    BillLineResponse getBillLineById(long billLineId);
}
