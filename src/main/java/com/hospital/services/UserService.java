package com.hospital.services;

import com.hospital.entities.Doctor;
import com.hospital.entities.User;
import com.hospital.payload.response.MessageResponse;

import java.util.Optional;

public interface UserService {

    MessageResponse sendPasswordResetEmail(String email);

    Optional<User> findUserById(Long id);

    boolean resetPassword(String token, String newPassword);

    Optional<Doctor> findDoctorByUserId(Long userId);
}
