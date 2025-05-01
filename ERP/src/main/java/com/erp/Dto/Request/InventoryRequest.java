package com.erp.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {

    @Pattern(regexp = "^.{2,}$",message = "Please Enter the More then 2 Character")
    @NotBlank(message="Please Enter Value")
    private String itemName;

    private String itemQuantity;
    private String itemDescription;
    private double itemCost;

}
