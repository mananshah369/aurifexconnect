package com.erp.Model;

import com.erp.Enum.AccountStatus;
import com.erp.Enum.Banks;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountId;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Banks bankName;

    private double openingBalance;

    private double currentBalance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @CreatedBy
    private String CreatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @OneToOne
    private Ledger ledger;

    @OneToMany(mappedBy = "bankAccount")
    private List<Master> masters;

}
