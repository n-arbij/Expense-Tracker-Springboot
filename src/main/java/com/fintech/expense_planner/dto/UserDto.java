package com.fintech.expense_planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDto {
    @Data
    public static class RegisterDto{
        private String username;
        private String email;
        private String password;
    }

    @Data
    public static class LoginDto{
        private String username;
        private String password;
    }

    @Data
    public static class UpdateUserDto{
        private String username;
        private String email;
        private String password;
    }

    @Data
    public static class UserResponseDto{
        private String id;
        private String username;
        private String email;
        private String role;
        private boolean isActive;
    }

    @Data
    @AllArgsConstructor
    public static class AuthResponse{
        private String token;
        private String username;
    }
}
