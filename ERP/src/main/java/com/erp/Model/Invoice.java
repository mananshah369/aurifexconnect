package com.erp.Model;


import com.erp.Enum.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    private LocalDate invoiceDate;

    private String description;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL)
    private List<InvoiceLineItems> invoiceLineItems;

}
