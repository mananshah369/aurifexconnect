package com.erp.Dto.Response;


import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class LeaveResponse {
    private long id;
    private UserResponse user;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String reason;
    private String status;
}
