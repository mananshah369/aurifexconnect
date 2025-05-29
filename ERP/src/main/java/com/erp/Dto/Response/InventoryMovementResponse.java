package com.erp.Dto.Response;

import com.erp.Model.Branch;
import com.erp.Model.Ledger;
import com.erp.Model.LineItems;
import com.erp.Model.Voucher;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventoryMovementResponse {

    private long tranId;

    private double itemQuantity;

    private LocalDateTime tranDate;

}
