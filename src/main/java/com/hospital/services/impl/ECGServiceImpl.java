package com.hospital.services.impl;

import com.hospital.entities.ECG;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.ECGRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.ECGService;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<ECG> getAllEcgsByMedicalDossierId(Long medicalDossierId) {
        Optional<MedicalDossier> optionalMedicalDossier = medicalDossierService.getMedicalDossierById(medicalDossierId);
        if (optionalMedicalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalMedicalDossier.get();
            return medicalDossier.getEcgs();
        } else {
            return Collections.emptyList();
        }
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
    @Override
    public List<ECG> getAllEcgs() {
        return ecgRepository.findAll();
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
