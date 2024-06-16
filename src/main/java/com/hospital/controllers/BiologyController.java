package com.hospital.controllers;

import com.hospital.entities.Biology;
import com.hospital.entities.ECG;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BiologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/biologies")
public class BiologyController {

    @Autowired
    BiologyService biologyService;

    @GetMapping("/medical-dossier/{medicalDossierId}")
    public ResponseEntity<List<Biology>> getAllBiologiesWithLinks(@PathVariable Long medicalDossierId) {
        List<Biology> biologies = biologyService.getAllBiologiesByMedicalDossierId(medicalDossierId);
        String baseUrl = "http://localhost:8080/api/biologies/files/";

        biologies.forEach(biology -> {
            if (biology.getBilanImageUrl() != null) {
                biology.setBilanImageUrl(baseUrl + biology.getBilanImageUrl());
            }
        });

        return ResponseEntity.ok(biologies);
    }


    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadBiologyData(
            @RequestParam("bilanFile") MultipartFile bilanFile,
            @RequestParam("conclusion") String conclusion,
            @PathVariable("patientId") Long patientId) {
        if (bilanFile == null || bilanFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Le fichier d'image de biologie est vide !"));
        }
        biologyService.uploadBiologyData(bilanFile, conclusion, patientId);
        return ResponseEntity.ok(new MessageResponse("La biologie a été enregistrée avec succès !"));
    }

    @GetMapping(value = "/files/{filename:[a-zA-Z0-9._-]+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = biologyService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
