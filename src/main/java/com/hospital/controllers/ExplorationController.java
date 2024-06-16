package com.hospital.controllers;

import com.hospital.entities.ECG;
import com.hospital.entities.Exploration;
import com.hospital.entities.ExplorationType;
import com.hospital.entities.MedicalDossier;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BiologyService;
import com.hospital.services.ExplorationService;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/explorations")
public class ExplorationController {

    @Autowired
    ExplorationService explorationService;

    @Autowired
    MedicalDossierService medicalDossierService;
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadExplorationData(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("conclusion") String conclusion,
            @RequestParam("explorationType") ExplorationType explorationType,
            @PathVariable("patientId") Long patientId) {
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("L'image de l'exploration est vide."));
        }
        explorationService.uploadExplorationData(imageFile, conclusion, patientId, explorationType);
        return ResponseEntity.ok(new MessageResponse(" a été enregistrée avec succès !"));
    }


    @GetMapping("/rt/{medicalDossierId}")
    public ResponseEntity<List<Exploration>> getAllExplorationsRTByMedicalDossierId(@PathVariable Long medicalDossierId) {
        List<Exploration> explorations = explorationService.getAllExplorationsRTByMedicalDossierId(medicalDossierId);
        String baseUrl = "http://localhost:8080/api/explorations/files/";

        explorations.forEach(exploration -> {
            if (exploration.getImageUrl() != null) {
                exploration.setImageUrl(baseUrl + exploration.getImageUrl());
            }
        });
        return ResponseEntity.ok(explorations);
    }

    @GetMapping("/echo/{medicalDossierId}")
    public ResponseEntity<List<Exploration>> getAllExplorationsEchoCGWithLinks(@PathVariable Long medicalDossierId) {
        List<Exploration> explorations = explorationService.getAllExplorationsEchoCGWithLinks(medicalDossierId);
        String baseUrl = "http://localhost:8080/api/explorations/files/";

        explorations.forEach(exploration -> {
            if (exploration.getImageUrl() != null) {
                exploration.setImageUrl(baseUrl + exploration.getImageUrl());
            }
        });
        return ResponseEntity.ok(explorations);
    }

    @GetMapping(value = "/files/{filename:[a-zA-Z0-9._-]+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = explorationService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
