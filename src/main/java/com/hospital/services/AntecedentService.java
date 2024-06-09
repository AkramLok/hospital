package com.hospital.services;

import com.hospital.entities.Antecedent;

import java.util.List;

public interface AntecedentService {
    void addAntecedentToPatient(Long patientId, Antecedent antecedent);

    List<Antecedent> getAllAntecedentsByMedicalDossierId(Long medicalDossierId);
}
