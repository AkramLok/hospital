package com.hospital.repositories;

import com.hospital.entities.ECG;
import com.hospital.entities.MedicalDossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ECGRepository extends JpaRepository<ECG, Long> {
}
