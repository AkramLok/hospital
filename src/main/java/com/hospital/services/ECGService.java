package com.hospital.services;

import org.springframework.web.multipart.MultipartFile;

public interface ECGService {
    void uploadECGData(MultipartFile imageFile, String conclusion, Long patientId);
}
