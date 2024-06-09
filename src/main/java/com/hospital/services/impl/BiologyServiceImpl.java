package com.hospital.services.impl;

import com.hospital.entities.Biology;
import com.hospital.entities.MedicalDossier;
import com.hospital.entities.Patient;
import com.hospital.repositories.BiologyRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.BiologyService;
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


    @Override
    public List<Biology> getAllBiologiesByMedicalDossierId(Long medicalDossierId) {
        Optional<MedicalDossier> optionalMedicalDossier = medicalDossierService.getMedicalDossierById(medicalDossierId);
        if (optionalMedicalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalMedicalDossier.get();
            return medicalDossier.getBiologies();
        } else {
            return Collections.emptyList();
        }
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
