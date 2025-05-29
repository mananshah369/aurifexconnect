package com.erp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tranId;

    @ManyToOne
    private Voucher voucher; // links to voucher table

    @ManyToOne
    private LineItems lineItem; // links to LineItems table

    @ManyToOne
    private Branch branch; // track branch where stock is affected

    @ManyToOne
    private Inventory inventory;  //track inventory table

    @ManyToOne
    private Ledger ledger; // links to ledger table

    private double itemQuantity;

    @CreatedDate
    private LocalDateTime tranDate;
}