package com.hospital.services;

import com.hospital.entities.Exploration;
import com.hospital.entities.ExplorationType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExplorationService {
    void uploadExplorationData(MultipartFile imageFile, String conclusion, Long patientId, ExplorationType explorationType);

    List<Exploration> getAllExplorationsRTByMedicalDossierId(Long medicalDossierId);

    List<Exploration> getAllExplorationsEchoCGWithLinks(Long medicalDossierId);

    Resource load(String filename);
}
