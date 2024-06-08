package com.hospital.services;

import com.hospital.entities.Antecedent;

public interface AntecedentService {
    void addAntecedentToPatient(Long patientId, Antecedent antecedent);
}
