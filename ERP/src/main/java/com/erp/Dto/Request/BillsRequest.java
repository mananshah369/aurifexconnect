package com.erp.Dto.Request;

import com.erp.Enum.AmountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class BillsRequest {

    @NotNull(message = "Transaction date is required")
    private LocalDate tranDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotBlank(message = "Description is required")
    @Pattern(regexp = "^[A-Za-z0-9 ,.-]{3,100}$", message = "Description must be 3–100 characters and contain only letters, numbers, spaces, commas, dots, or hyphens")
    private String description;

    @NotBlank(message = "Reference bill number is required")
    @Pattern(regexp = "^[A-Z0-9_-]{3,20}$", message = "Reference bill number must be 3–20 characters long, uppercase letters, numbers, underscores or hyphens")
    private String referenceBillNo;

    @Positive(message = "Total amount must be greater than zero")
    private double totalAmount;

    @NotNull(message = "Amount status is required")
    private AmountStatus status;


}
