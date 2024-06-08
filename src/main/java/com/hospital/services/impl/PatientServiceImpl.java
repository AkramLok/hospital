package com.hospital.services.impl;

import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Override
    @Transactional
    public Patient addPatient(Patient patient) {
        // Check if the patient already has a medical dossier
        if (patient.getMedicalDossier() == null) {
            // If not, create a new medical dossier for the patient
            MedicalDossier medicalDossier = new MedicalDossier();
            // Set any necessary properties for the medical dossier
            // For example:
            // medicalDossier.setPatient(patient); // if there is a bidirectional relationship
            // medicalDossier.set... // set other properties as needed

            // Set the medical dossier for the patient
            patient.setMedicalDossier(medicalDossier);
        }

        // Save the patient (and cascade save the associated medical dossier)
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
}
