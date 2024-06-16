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
        if (patient.getReferenceID() == null || patient.getReferenceID().isEmpty()) {
            patient.setReferenceID(generateReferenceID(patient));
        } else if (patientRepository.findByReferenceID(patient.getReferenceID()).isPresent()) {
            throw new IllegalArgumentException("Reference ID already exists");
        }

        if (patient.getMedicalDossier() == null) {
            MedicalDossier medicalDossier = new MedicalDossier();
            patient.setMedicalDossier(medicalDossier);
        }

        return patientRepository.save(patient);
    }
    private String generateReferenceID(Patient patient) {
        // Example: Use initials of the patient name, current timestamp, and a random number
        String initials = patient.getNom().substring(0, 1) + patient.getPrenom().substring(0, 1);
        long timestamp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * 1000);
        return initials.toUpperCase() + "-" + timestamp + "-" + randomNum;
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
            if (patient.getBed() != null) {
                Bed bed = patient.getBed();
                bed.setCurrentPatient(null);
                bed.setStartDateTime(null);
                bed.setState(BedState.EMPTY);
                patient.setBed(null);
                bedRepository.save(bed);
            }
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
