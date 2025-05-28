package com.erp.Service.LineItems;

import com.erp.Dto.Response.LineItemsResponse;

public interface LineItemService {

    LineItemsResponse createLineItems(long itemId,long masterId,long ledgerId , double quantity);

//    LineItemsResponse updateLineItem(long lineItemId, double newQuantity, long ledgerId);

//    void deleteLineItem(long lineItemId, long ledgerId);
}
