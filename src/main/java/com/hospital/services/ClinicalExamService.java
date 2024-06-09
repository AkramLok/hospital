package com.hospital.services;

import com.hospital.entities.Antecedent;
import com.hospital.entities.ClinicalExam;

import java.util.List;

public interface ClinicalExamService {
    List<ClinicalExam> getClinicalExamsBySection(String section, Long medicalDossierId);

    void addClinicalExamToPatient(Long patientId, ClinicalExam clinicalExam);
}
