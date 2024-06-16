package com.hospital.services;

import com.hospital.entities.Patient;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PatientService {
    Patient addPatient(Patient patient);

    Patient updatePatient(Long id, Patient updatedPatient);

    @Transactional
    void deletePatient(Long patientId);

    List<Patient> getAllPatients();
    Patient getPatientById(Long id);

}
