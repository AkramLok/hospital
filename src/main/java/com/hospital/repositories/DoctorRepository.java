package com.hospital.repositories;

import com.hospital.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByActive(Boolean active);

    default void deactivateDoctor(Long id) {
        Doctor doctor = findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(false);
        save(doctor);
    }

    default void activateDoctor(Long id) {
        Doctor doctor = findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(true);
        save(doctor);
    }
}

