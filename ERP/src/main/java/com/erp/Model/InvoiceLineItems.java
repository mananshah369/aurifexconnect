package com.erp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class InvoiceLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceLineItemId;

    private LocalDate date;

    private String itemName;

    private double unitPrice;

    private double quantity;

    private double totalPrice;

    @ManyToOne
    private Inventory inventory;

    @ManyToOne
    private Invoice invoice;
}
