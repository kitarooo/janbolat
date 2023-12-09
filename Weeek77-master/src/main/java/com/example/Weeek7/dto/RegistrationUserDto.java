package com.example.Weeek7.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationUserDto {
    private String username;
    private String password;
    private String email;
}
