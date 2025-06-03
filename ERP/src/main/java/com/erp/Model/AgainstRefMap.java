package com.erp.Model;

import com.erp.Enum.ReferenceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AgainstRefMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long againstId;

    private ReferenceType referenceType;

    private double amount;

    @ManyToOne
    private Ledger ledger;

    @ManyToOne
    private Master master;

}
