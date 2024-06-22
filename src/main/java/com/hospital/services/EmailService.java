package com.hospital.services;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token);

    void sendEmail(String to, String subject, String message);
}
