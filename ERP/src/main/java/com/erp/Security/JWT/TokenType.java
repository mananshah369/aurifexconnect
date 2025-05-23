package com.erp.Security.JWT;

public enum TokenType {
    ACCESS("at"), REFRESH("rt");

    private final String type;

    TokenType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
