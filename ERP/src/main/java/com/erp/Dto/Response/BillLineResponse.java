package com.erp.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BillLineResponse {

    private long billLineId;

    private String description;

    private int quantity;

    private double unitPrice;

    private double totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
