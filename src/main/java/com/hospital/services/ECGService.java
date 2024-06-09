package com.hospital.services;

import com.hospital.entities.ECG;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ECGService {
    void uploadECGData(MultipartFile imageFile, String conclusion, Long patientId);
    List<ECG> getAllEcgsByMedicalDossierId(Long medicalDossierId);

    List<ECG> getAllEcgs();

    Resource load(String filename);
}
