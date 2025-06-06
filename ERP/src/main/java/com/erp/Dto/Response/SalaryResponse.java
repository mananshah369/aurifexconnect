package com.erp.Dto.Response;

import java.time.YearMonth;
import com.erp.Enum.AmountStatus;
import lombok.*;

@Getter
@Setter
public class SalaryResponse {
    private long id;
    private UserResponse user;
    private YearMonth month;
    private Double baseSalary;
    private Integer workingDays;
    private Integer paidDays;
    private Double deductions;
    private Double bonus;
    private Double netSalary;
    private String remarks;
    private AmountStatus amountStatus;
    private YearMonth paymentDate;
}
