package com.hospital.services.impl;

import com.hospital.entities.Biology;
import com.hospital.entities.ECG;
import com.hospital.entities.Patient;
import com.hospital.repositories.BiologyRepository;
import com.hospital.repositories.ECGRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.BiologyService;
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
public class BiologyServiceImpl implements BiologyService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BiologyRepository biologyRepository;

    @Autowired
    private MedicalDossierService medicalDossierService;
    private final Path root = Paths.get("uploads/biology");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public void uploadBiologyData(MultipartFile bilanFile, String conclusion, Long patientId) {
        init();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String randomFileName = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(bilanFile.getOriginalFilename());
        String combinedFileName = randomFileName + "." + fileExtension;
        saveBiologyImage(bilanFile, combinedFileName);

        Biology biology = new Biology();
        biology.setBilanImageUrl(combinedFileName);
        biology.setConclusion(conclusion);
        biology.setMedicalDossier(medicalDossierService.getMedicalDossier(patientId));

        biologyRepository.save(biology);
    }

    public void saveBiologyImage(MultipartFile file, String filename) {
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
