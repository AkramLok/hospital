package com.hospital.services.impl;

import com.hospital.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class.getName());

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;
        String message = "To reset your password, click the link below:\n" + resetUrl;
        logger.info("Preparing to send email to " + to);
        sendEmail(to, subject, message);
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(message);
            logger.info("Sending email to " + to + " with subject " + subject);
            mailSender.send(email);
            logger.info("Email sent successfully to " + to);
        } catch (Exception e) {
            logger.severe("Failed to send email to " + to + " with subject " + subject + ". Error: " + e.getMessage());
        }
    }
}
