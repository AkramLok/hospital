package com.hospital.services;

import com.hospital.entities.Antecedent;
import com.hospital.entities.ClinicalExam;

public interface ClinicalExamService {
    void addClinicalExamToPatient(Long patientId, ClinicalExam clinicalExam);
}
