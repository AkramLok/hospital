package com.hospital.services.impl;

import com.hospital.entities.Doctor;
import com.hospital.entities.PasswordResetToken;
import com.hospital.entities.User;
import com.hospital.repositories.PasswordResetTokenRepository;
import com.hospital.repositories.UserRepository;
import com.hospital.services.EmailService;
import com.hospital.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public boolean sendPasswordResetEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken(token, user);
            tokenRepository.save(resetToken);
            emailService.sendPasswordResetEmail(user.getEmail(), token);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isPresent() && !tokenOptional.get().isExpired()) {
            User user = tokenOptional.get().getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            tokenRepository.delete(tokenOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Doctor> findDoctorByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getDoctor);
    }
}
