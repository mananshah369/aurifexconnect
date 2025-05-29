package com.erp.Dto.Response;

import com.erp.Enum.BranchStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BranchResponse {

    private long branchId;
    private String branchName;
    private String location;
    private String contactInfo;
    private LocalDateTime createdAt;
    private BranchStatus branchStatus;
}
