package com.erp.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LedgerResponse {

    private long ledgerId;

    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime created_at;
    private LocalDateTime updatedAt;


}
