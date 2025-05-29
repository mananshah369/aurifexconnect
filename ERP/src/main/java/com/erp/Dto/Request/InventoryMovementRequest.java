package com.erp.Dto.Request;

import com.erp.Model.*;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMovementRequest {

    private long voucherId;
    private long lineItemId;
    private long branchId;
    private long inventoryId;
    private long ledgerId;
    private double itemQuantity;

}
