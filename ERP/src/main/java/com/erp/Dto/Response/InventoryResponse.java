package com.erp.Dto.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InventoryResponse {

    private long itemId;

    private String itemName;
    private double itemQuantity;
    private String itemDescription;
    private double itemCost;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
