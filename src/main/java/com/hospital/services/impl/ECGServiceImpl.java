package com.hospital.services.impl;

import com.hospital.entities.ECG;
import com.hospital.entities.Patient;
import com.hospital.repositories.ECGRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.ECGService;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ECGServiceImpl implements ECGService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ECGRepository ecgRepository;

    @Autowired
    private MedicalDossierService medicalDossierService;
    private final Path root = Paths.get("uploads/ecg");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public void uploadECGData(MultipartFile imageFile, String conclusion, Long patientId) {
        init();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String randomFileName = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        String combinedFileName = randomFileName + "." + fileExtension;
        saveECGImage(imageFile, combinedFileName);

        ECG ecg = new ECG();
        ecg.setImageUrl(combinedFileName);
        ecg.setConclusion(conclusion);
        ecg.setMedicalDossier(medicalDossierService.getMedicalDossier(patientId));

        ecgRepository.save(ecg);
    }

    public void saveECGImage(MultipartFile file, String filename) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

}
