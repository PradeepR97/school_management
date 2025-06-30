package com.schoolmanagement.dto;

import com.schoolmanagement.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
private Role role;
}
