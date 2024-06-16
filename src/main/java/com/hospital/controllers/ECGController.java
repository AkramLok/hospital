package com.hospital.controllers;

import com.hospital.entities.ECG;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.ECGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ecgs")
public class ECGController {

    @Autowired
    private ECGService ecgService;

    @GetMapping("/medical-dossier/{medicalDossierId}")
    public ResponseEntity<List<ECG>> getAllEcgsWithLinks(@PathVariable Long medicalDossierId) {
        List<ECG> ecgs = ecgService.getAllEcgsByMedicalDossierId(medicalDossierId);
        String baseUrl = "http://localhost:8080/api/ecgs/files/";

        ecgs.forEach(ecg -> {
            if (ecg.getImageUrl() != null) {
                ecg.setImageUrl(baseUrl + ecg.getImageUrl());
            }
        });

        return ResponseEntity.ok(ecgs);
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadECGImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("conclusion") String conclusion,
            @PathVariable("patientId") Long patientId) {
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Le fichier image de l'ECG est vide !"));
        }
        ecgService.uploadECGData(imageFile, conclusion, patientId);
        return ResponseEntity.ok(new MessageResponse("ECG enregistré avec succès !"));
    }

    @GetMapping(value = "/files/{filename:[a-zA-Z0-9._-]+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = ecgService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
