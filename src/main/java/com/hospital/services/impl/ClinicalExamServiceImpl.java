package com.hospital.services.impl;

import com.hospital.entities.Antecedent;
import com.hospital.entities.ClinicalExam;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.AntecedentRepository;
import com.hospital.repositories.ClinicalExamRepository;
import com.hospital.repositories.MedicalDossierRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.AntecedentService;
import com.hospital.services.ClinicalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicalExamServiceImpl implements ClinicalExamService {

    @Autowired
    private MedicalDossierRepository medicalDossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicalExamRepository clinicalExamRepository;

    @Override
    public void addClinicalExamToPatient(Long patientId, ClinicalExam clinicalExam) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalDossier medicalDossier = patient.getMedicalDossier();
        if (medicalDossier == null) {
            throw new RuntimeException("Medical dossier not found for the patient");
        }

        clinicalExam.setMedicalDossier(medicalDossier);
        clinicalExamRepository.save(clinicalExam);
    }
}