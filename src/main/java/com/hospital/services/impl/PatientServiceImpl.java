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
        String initials =  patient.getPrenom().substring(0, 1)+patient.getNom().substring(0, 1);
        long timestamp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * 1000);
        return initials.toUpperCase() + "-" + timestamp + "-" + randomNum;
    }

    @Override
    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();

            // Update fields only if they are not null or empty
            if (updatedPatient.getNom() != null && !updatedPatient.getNom().isEmpty()) {
                patient.setNom(updatedPatient.getNom());
            }
            if (updatedPatient.getPrenom() != null && !updatedPatient.getPrenom().isEmpty()) {
                patient.setPrenom(updatedPatient.getPrenom());
            }
            if (updatedPatient.getAge() != null) {
                patient.setAge(updatedPatient.getAge());
            }
            if (updatedPatient.getVille() != null && !updatedPatient.getVille().isEmpty()) {
                patient.setVille(updatedPatient.getVille());
            }
            if (updatedPatient.getRegion() != null && !updatedPatient.getRegion().isEmpty()) {
                patient.setRegion(updatedPatient.getRegion());
            }
            if (updatedPatient.getFullAddress() != null && !updatedPatient.getFullAddress().isEmpty()) {
                patient.setFullAddress(updatedPatient.getFullAddress());
            }
            if (updatedPatient.getFamilyPhone() != null && !updatedPatient.getFamilyPhone().isEmpty()) {
                patient.setFamilyPhone(updatedPatient.getFamilyPhone());
            }
            if (updatedPatient.getMaritalStatus() != null && !updatedPatient.getMaritalStatus().isEmpty()) {
                patient.setMaritalStatus(updatedPatient.getMaritalStatus());
            }
            if (updatedPatient.getAssurance() != null && !updatedPatient.getAssurance().isEmpty()) {
                patient.setAssurance(updatedPatient.getAssurance());
            }
            if (updatedPatient.getProfession() != null && !updatedPatient.getProfession().isEmpty()) {
                patient.setProfession(updatedPatient.getProfession());
            }
            if (updatedPatient.getReferenceID() != null && !updatedPatient.getReferenceID().isEmpty()) {
                patient.setReferenceID(updatedPatient.getReferenceID());
            }

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
