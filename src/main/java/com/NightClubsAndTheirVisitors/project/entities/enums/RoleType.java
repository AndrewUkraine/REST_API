package com.NightClubsAndTheirVisitors.project.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    USER,
    SUPER_USER,
    UNKNOWN_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}

