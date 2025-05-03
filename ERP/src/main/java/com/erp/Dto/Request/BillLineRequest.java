package com.erp.Dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillLineRequest {

    @NotBlank(message = "Description is required")
    @Pattern(
            regexp = "^[A-Za-z0-9 ,.-]{3,100}$",
            message = "Description must be 3–100 characters and can contain letters, numbers, spaces, commas, periods, or hyphens"
    )
    private String description;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Positive(message = "Unit price must be greater than zero")
    private double unitPrice;


}
