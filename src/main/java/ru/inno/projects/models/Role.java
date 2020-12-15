package ru.inno.projects.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, MEMBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
