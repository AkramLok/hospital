package com.hospital.services;

import com.hospital.entities.Antecedent;
import com.hospital.entities.Diagnosis;

import javax.tools.Diagnostic;
import java.util.List;

public interface DiagnosisService {
    void addDiagnosisToPatient(Long patientId, Diagnosis diagnosis);

    List<Diagnosis> getAllDiagnosesByMedicalDossierId(Long medicalDossierId);
}
