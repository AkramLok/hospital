package com.hospital.repositories;

import com.hospital.entities.ClinicalExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicalExamRepository extends JpaRepository<ClinicalExam, Long> {
    List<ClinicalExam> findByAbdominalIsNotNullAndMedicalDossierId(Long medicalDossierId);
    List<ClinicalExam> findByPulmonaryIsNotNullAndMedicalDossierId(Long medicalDossierId);
    List<ClinicalExam> findByPulmonaryIsNullAndAbdominalIsNullAndMedicalDossierId(Long medicalDossierId);
}
