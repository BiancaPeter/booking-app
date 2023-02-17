package com.spring.booking.DTO;

import com.spring.booking.model.RoleType;

public class RegisterDTO {

    private String username;
    private String password;

    private RoleType roleType;

    public RegisterDTO(String username, String password,RoleType roleType) {
        this.username = username;
        this.password = password;
        this.roleType =roleType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}

