package com.hospital.repositories;

import com.hospital.entities.ClinicalExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalExamRepository extends JpaRepository<ClinicalExam, Long> {
}
