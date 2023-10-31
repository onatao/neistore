package com.neidev.store.security.enums;

import java.util.Arrays;

public enum AuthRole {

    ADMIN(0, "admin"),
    BUYER(1, "buyer"),
    SELLER(2, "seller");

    private final Integer code;
    private final String description;

    AuthRole(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public AuthRole toEnum(Integer code) {
        if (code == null) return null;
        for(AuthRole o: AuthRole.values()) {
            if (code.equals(o.getCode())) return o;
        }
        throw new IllegalArgumentException("Cannot get role");
    }
}
