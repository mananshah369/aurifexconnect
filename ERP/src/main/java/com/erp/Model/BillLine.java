package com.erp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BillLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_line_id")
    private long billLineId;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "unitPrice")
    private double unitPrice;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    private Bills bill;

    @ManyToOne
    private Inventory inventory;


}
