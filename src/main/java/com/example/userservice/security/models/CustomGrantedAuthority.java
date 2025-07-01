package com.example.userservice.security.models;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private String value;

    public CustomGrantedAuthority(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return this.value;
    }
}
