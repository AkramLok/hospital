package com.hospital.controllers;

import com.hospital.entities.ExplorationType;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BiologyService;
import com.hospital.services.ExplorationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/explorations")
public class ExplorationController {

    @Autowired
    ExplorationService explorationService;
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadExplorationData(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("conclusion") String conclusion,
            @RequestParam("explorationType") ExplorationType explorationType,
            @PathVariable("patientId") Long patientId) {
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Exploration image file is empty!"));
        }
        explorationService.uploadExplorationData(imageFile, conclusion, patientId, explorationType);
        return ResponseEntity.ok(new MessageResponse("Exploration saved successfully!"));
    }
}
