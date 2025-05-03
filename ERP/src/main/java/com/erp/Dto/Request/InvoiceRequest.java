package com.erp.Dto.Request;

import com.erp.Enum.InvoiceStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {
    private String description;
}
