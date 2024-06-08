package com.hospital.services;

import com.hospital.entities.Patient;
import java.util.List;

public interface PatientService {
    Patient addPatient(Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
}
