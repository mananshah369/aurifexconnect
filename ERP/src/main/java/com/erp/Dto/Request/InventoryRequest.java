package com.erp.Dto.Request;

import com.erp.Dto.Response.InventoryResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryRequest {

    private String itemName;
    private String itemQuantity;
    private String itemDescription;
    private double itemCost;

}
