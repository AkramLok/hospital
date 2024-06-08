package com.hospital.services;

import org.springframework.web.multipart.MultipartFile;

public interface BiologyService {
    void uploadBiologyData(MultipartFile bilanFile, String conclusion, Long patientId);
}
