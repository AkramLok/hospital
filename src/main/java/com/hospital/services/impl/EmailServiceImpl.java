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
        String subject = "Demande de réinitialisation de mot de passe";
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;
        String message = "Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous :\n" + resetUrl;
        logger.info("Préparation de l'envoi de l'email à " + to);
        sendEmail(to, subject, message);
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(message);
            logger.info("Envoi d'email à " + to + " avec le sujet " + subject);
            mailSender.send(email);
            logger.info("Email envoyé avec succès à " + to);
        } catch (Exception e) {
            logger.severe("Échec de l'envoi d'email à " + to + " avec le sujet " + subject + ". Erreur : " + e.getMessage());
        }
    }
}
