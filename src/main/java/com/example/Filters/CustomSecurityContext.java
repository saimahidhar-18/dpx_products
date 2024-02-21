package com.example.Filters;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Priority((Priorities.AUTHORIZATION))
public class CustomSecurityContext implements SecurityContext {

    private final String username;
    private final String role;

    public CustomSecurityContext(String username, String role, SecurityContext securityContext) {
        this.username = username;
        this.role = role;
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
    }


    @Override
    public boolean isUserInRole(String role) {
        return this.role.equals(role);
    }

    @Override
    public boolean isSecure() {
        // Implement as needed
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        // Implement as needed
        return null;
    }
}

