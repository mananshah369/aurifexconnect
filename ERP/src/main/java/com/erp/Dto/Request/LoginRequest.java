package com.example.dio.dto.request;


import com.example.dio.dto.rules.Email;
import com.example.dio.dto.rules.Password;

public record LoginRequest(
        @Email String email,
        @Password String password
) {
}
