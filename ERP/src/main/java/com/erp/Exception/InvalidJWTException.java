package com.example.dio.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidJWTException extends RuntimeException {
    private final String message;
}
