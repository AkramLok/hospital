package com.hospital.services;

import com.hospital.entities.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);

    Doctor saveDoctor(Doctor doctor);

    void deleteDoctor(Long id);

}
