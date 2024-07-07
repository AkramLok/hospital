package com.hospital.services;

import com.hospital.entities.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> getAllActiveDoctors();

    List<Doctor> getAllNonActiveDoctors();

    Optional<Doctor> getDoctorById(Long id);

    Doctor saveDoctor(Doctor doctor);

    void deleteDoctor(Long id);

    void deactivateDoctor(Long id);

    void activateDoctor(Long id);
}
