package com.erp.Dto.Response;

import com.erp.Enum.AmountStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BillsResponse {

    private long billId;

    private LocalDate tranDate;

    private LocalDate dueDate;

    private String description;

    private String referenceBillNo;

    private double totalAmount;

    private AmountStatus status;

}
