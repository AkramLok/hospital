package com.hospital.services.impl;

import com.hospital.entities.Antecedent;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.AntecedentRepository;
import com.hospital.repositories.MedicalDossierRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.AntecedentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntecedentServiceImpl implements AntecedentService {

    @Autowired
    private MedicalDossierRepository medicalDossierRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AntecedentRepository antecedentRepository;

    @Override
    public void addAntecedentToPatient(Long patientId, Antecedent antecedent) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalDossier medicalDossier = patient.getMedicalDossier();
        if (medicalDossier == null) {
            throw new RuntimeException("Medical dossier not found for the patient");
        }

        antecedent.setMedicalDossier(medicalDossier);
        antecedentRepository.save(antecedent);
    }
    @Override
    public List<Antecedent> getAllAntecedentsByMedicalDossierId(Long medicalDossierId) {
        return antecedentRepository.findByMedicalDossierId(medicalDossierId);
    }
}
