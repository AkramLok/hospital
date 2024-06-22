package com.hospital.dto;

import lombok.Data;

@Data
public class PasswordResetForm {
    private String token;
    private String newPassword;
}