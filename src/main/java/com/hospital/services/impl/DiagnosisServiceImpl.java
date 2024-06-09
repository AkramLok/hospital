package com.hospital.services.impl;

import com.hospital.entities.Diagnosis;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.DiagnosisRepository;
import com.hospital.repositories.MedicalDossierRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    private MedicalDossierRepository medicalDossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Override
    public void addDiagnosisToPatient(Long patientId, Diagnosis diagnosis) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalDossier medicalDossier = patient.getMedicalDossier();
        if (medicalDossier == null) {
            throw new RuntimeException("Medical dossier not found for the patient");
        }

        diagnosis.setMedicalDossier(medicalDossier);
        diagnosisRepository.save(diagnosis);
    }

    @Override
    public List<Diagnosis> getAllDiagnosesByMedicalDossierId(Long medicalDossierId) {
        return diagnosisRepository.findByMedicalDossierId(medicalDossierId);
    }
}
