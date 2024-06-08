package com.hospital.services;

import com.hospital.entities.ExplorationType;
import org.springframework.web.multipart.MultipartFile;

public interface ExplorationService {
    void uploadExplorationData(MultipartFile imageFile, String conclusion, Long patientId, ExplorationType explorationType);
}
