package com.erp.Model;


import com.erp.Enum.AmountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private long billId;

    @Column(name = "tran_date")
    private LocalDate tranDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "description")
    private String description;

    @Column(name = "reference_bill_no")
    private String referenceBillNo;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "status")
    private AmountStatus status;

    @ManyToOne
    private Ledger ledger;

    @OneToMany(mappedBy = "bill")
    private List<BillLine> billLines;


}
