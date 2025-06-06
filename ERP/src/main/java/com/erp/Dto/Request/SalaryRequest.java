package com.erp.Dto.Request;

import lombok.*;
import java.time.YearMonth;

@Getter
@Setter
public class SalaryRequest {
    private long id;
    private long userId;
    private YearMonth month;
    private Double baseSalary;
    private Double bonus;
    private Double deductions;
    private Integer paidDays;
    private String remarks;
}