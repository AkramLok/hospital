package com.hospital.services.impl;

import com.hospital.entities.*;
import com.hospital.repositories.ExplorationRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.ExplorationService;
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
import java.util.stream.Collectors;

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

    @Override
    public List<Exploration> getAllExplorationsRTByMedicalDossierId(Long medicalDossierId) {
        Optional<MedicalDossier> optionalMedicalDossier = medicalDossierService.getMedicalDossierById(medicalDossierId);
        if (optionalMedicalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalMedicalDossier.get();
            return medicalDossier.getExplorations().stream()
                    .filter(exploration -> exploration.getType() == ExplorationType.RADIO_THORAX)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Exploration> getAllExplorationsEchoCGWithLinks(Long medicalDossierId) {
        Optional<MedicalDossier> optionalMedicalDossier = medicalDossierService.getMedicalDossierById(medicalDossierId);
        if (optionalMedicalDossier.isPresent()) {
            MedicalDossier medicalDossier = optionalMedicalDossier.get();
            return medicalDossier.getExplorations().stream()
                    .filter(exploration -> exploration.getType() == ExplorationType.ECHOCARDIOGRAPHIE)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
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
