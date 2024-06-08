package com.hospital.repositories;

import com.hospital.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.tools.Diagnostic;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
