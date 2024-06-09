package com.hospital.repositories;

import com.hospital.entities.Antecedent;
import com.hospital.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AntecedentRepository  extends JpaRepository<Antecedent, Long> {
    List<Antecedent> findByMedicalDossierId(Long medicalDossierId);
}
