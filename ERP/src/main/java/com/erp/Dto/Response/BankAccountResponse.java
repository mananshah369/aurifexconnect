package com.erp.Dto.Response;

import com.erp.Enum.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BankAccountResponse {

    private Long bankAccountId;
    private String accountNumber;
    private String bankName;
    private Double openingBalance;
    private Double currentBalance;
    private AccountStatus accountStatus;
    private String CreatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


}
