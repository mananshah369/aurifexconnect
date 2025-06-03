package com.erp.Model;

import com.erp.Enum.ReferenceType;
import com.erp.Enum.TransactionStatus;
import com.erp.Enum.VoucherType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long masterId;

    private String name;

    @Enumerated(EnumType.STRING)
    private VoucherType voucherType;

    private double amount;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String description;

    private String voucherIndex;

    @CreatedDate
    private LocalDateTime date;

    @ManyToOne
    private BankAccount bankAccount;

    @ManyToOne
    private Ledger ledger;

    @ManyToOne
    private Voucher voucher;

    @OneToMany(mappedBy = "master")
    private List<LineItems> lineItems;

    @OneToOne(mappedBy = "master")
    private  InvoiceGenerator invoiceGenerator;
}
