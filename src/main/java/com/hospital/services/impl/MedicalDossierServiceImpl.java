package com.hospital.services.impl;

import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.MedicalDossierRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalDossierServiceImpl implements MedicalDossierService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalDossierRepository medicalDossierRepository;

    @Override
    public MedicalDossier addMedicalDossier(Long patientId, MedicalDossier medicalDossier) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        medicalDossier.setPatient(patient);
        return medicalDossierRepository.save(medicalDossier);
    }

    @Override
    public MedicalDossier getMedicalDossier(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getMedicalDossier();
    }

    @Override
    public MedicalDossier updateMedicalDossier(Long patientId, MedicalDossier partialMedicalDossier) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalDossier existingMedicalDossier = patient.getMedicalDossier();

        if (existingMedicalDossier == null) {
            throw new RuntimeException("Medical dossier not found for the patient");
        }
        if (partialMedicalDossier.getHospitalization() != null) {
            existingMedicalDossier.setHospitalization(partialMedicalDossier.getHospitalization());
        }
        if (partialMedicalDossier.getHistoryDisease() != null) {
            existingMedicalDossier.setHistoryDisease(partialMedicalDossier.getHistoryDisease());
        }
        if (partialMedicalDossier.getPrimaryConclusion() != null) {
            existingMedicalDossier.setPrimaryConclusion(partialMedicalDossier.getPrimaryConclusion());
        }
        if (partialMedicalDossier.getConclusion() != null) {
            existingMedicalDossier.setConclusion(partialMedicalDossier.getConclusion());
        }

        return medicalDossierRepository.save(existingMedicalDossier);
    }

    @Override
    public void deleteMedicalDossier(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        MedicalDossier medicalDossier = patient.getMedicalDossier();
        if (medicalDossier != null) {
            patient.setMedicalDossier(null);
            patientRepository.save(patient);
            medicalDossierRepository.delete(medicalDossier);
        }
    }

    @Override
    public Optional<MedicalDossier> getMedicalDossierById(Long id) {
        return medicalDossierRepository.findById(id);
    }

    @Override
    public MedicalDossier archiveDossier(Long id) {
        Optional<MedicalDossier> optionalDossier = medicalDossierRepository.findById(id);
        if (optionalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalDossier.get();
            medicalDossier.setArchived(true);
            return medicalDossierRepository.save(medicalDossier);
        } else {
            throw new RuntimeException("Medical dossier not found with id " + id);
        }
    }

    @Override
    public MedicalDossier unarchiveDossier(Long id) {
        Optional<MedicalDossier> optionalDossier = medicalDossierRepository.findById(id);
        if (optionalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalDossier.get();
            medicalDossier.setArchived(false);
            return medicalDossierRepository.save(medicalDossier);
        } else {
            throw new RuntimeException("Medical dossier not found with id " + id);
        }
    }
}
