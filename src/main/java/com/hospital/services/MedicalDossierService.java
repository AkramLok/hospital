package com.hospital.services;

import com.hospital.entities.ECG;
import com.hospital.entities.MedicalDossier;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MedicalDossierService {
    MedicalDossier addMedicalDossier(Long patientId, MedicalDossier medicalDossier);
    MedicalDossier getMedicalDossier(Long patientId);
    MedicalDossier updateMedicalDossier(Long patientId, MedicalDossier medicalDossier);
    void deleteMedicalDossier(Long patientId);

    Optional<MedicalDossier> getMedicalDossierById(Long id);

    MedicalDossier archiveDossier(Long id);

    MedicalDossier unarchiveDossier(Long id);
}
