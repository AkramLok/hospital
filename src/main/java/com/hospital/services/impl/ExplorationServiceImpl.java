package com.hospital.services.impl;

import com.hospital.entities.Biology;
import com.hospital.entities.Exploration;
import com.hospital.entities.ExplorationType;
import com.hospital.entities.Patient;
import com.hospital.repositories.BiologyRepository;
import com.hospital.repositories.ExplorationRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.BiologyService;
import com.hospital.services.ExplorationService;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ExplorationServiceImpl implements ExplorationService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ExplorationRepository explorationRepository;

    @Autowired
    private MedicalDossierService medicalDossierService;
    private final Path root = Paths.get("uploads/exploration");
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void uploadExplorationData(MultipartFile imageUrl, String conclusion, Long patientId, ExplorationType explorationType) {
        init();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String randomFileName = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(imageUrl.getOriginalFilename());
        String combinedFileName = randomFileName + "." + fileExtension;
        saveExplorationImage(imageUrl, combinedFileName);

        Exploration exploration = new Exploration();
        exploration.setType(explorationType);
        exploration.setImageUrl(combinedFileName);
        exploration.setConclusion(conclusion);
        exploration.setMedicalDossier(medicalDossierService.getMedicalDossier(patientId));

       explorationRepository.save(exploration);
    }

    public void saveExplorationImage(MultipartFile file, String filename) {
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
