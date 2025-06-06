package com.erp.Model;

import com.erp.Enum.AmountStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.YearMonth;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListeners.class)
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private YearMonth month;

    @Column(nullable = false)
    private Double baseSalary;

    @Column(nullable = false)
    private Integer workingDays;

    @Column(nullable = false)
    private Integer paidDays;

    @Column(nullable = false)
    private Double deductions;

    @Column(nullable = false)
    private Double bonus;

    @Column(nullable = false)
    private Double netSalary;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmountStatus amountStatus;

    private YearMonth paymentDate;
}
