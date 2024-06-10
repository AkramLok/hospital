package com.hospital.repositories;

import com.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.bed IS NULL")
    List<Patient> findAllWithoutBed();

    Optional<Patient> findByReferenceID(String referenceID);

}
