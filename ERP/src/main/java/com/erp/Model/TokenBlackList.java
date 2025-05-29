package com.erp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String blackListId;

    private String token;

    private long expiration;
}
