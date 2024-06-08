package com.hospital.services;

import com.hospital.entities.Antecedent;
import com.hospital.entities.Diagnosis;

import javax.tools.Diagnostic;

public interface DiagnosisService {
    void addDiagnosisToPatient(Long patientId, Diagnosis diagnosis);
}
