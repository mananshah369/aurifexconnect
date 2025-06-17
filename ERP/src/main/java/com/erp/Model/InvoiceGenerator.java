package com.erp.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(EntityListeners.class)
public class InvoiceGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceGeneratedId;

    @Transient
    private List<LineItems> items;

    private double totalAmount;

    @OneToOne
    private Master master;

    @ManyToOne
    private Ledger ledger;

    @CreatedDate
    private LocalDateTime generatedAt;

}
