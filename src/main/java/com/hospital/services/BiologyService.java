package com.hospital.services;

import com.hospital.entities.Biology;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BiologyService {
    void uploadBiologyData(MultipartFile bilanFile, String conclusion, Long patientId);

    List<Biology> getAllBiologiesByMedicalDossierId(Long medicalDossierId);

    Resource load(String filename);
}
