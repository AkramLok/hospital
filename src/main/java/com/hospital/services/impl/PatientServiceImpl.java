package com.hospital.services.impl;

import com.hospital.entities.Bed;
import com.hospital.entities.BedState;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.BedRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BedRepository bedRepository;
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
    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setNom(updatedPatient.getNom());
            patient.setPrenom(updatedPatient.getPrenom());
            patient.setAge(updatedPatient.getAge());
            patient.setVille(updatedPatient.getVille());
            patient.setAssurance(updatedPatient.getAssurance());
            patient.setProfession(updatedPatient.getProfession());
            patient.setReferenceID(updatedPatient.getReferenceID());
            return patientRepository.save(patient);
        } else {
            throw new RuntimeException("Patient not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deletePatient(Long patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();

            // Remove bed assignment
            if (patient.getBed() != null) {
                Bed bed = patient.getBed();
                bed.setCurrentPatient(null);
                bed.setStartDateTime(null);
                bed.setState(BedState.EMPTY);
                patient.setBed(null);
                bedRepository.save(bed);
            }

            // Delete patient and cascade to medical dossier
            patientRepository.delete(patient);
        } else {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
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
