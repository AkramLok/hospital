package com.hospital.controllers;

import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BiologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/biologies")
public class BiologyController {

    @Autowired
    BiologyService biologyService;
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadBiologyData(
            @RequestParam("bilanFile") MultipartFile bilanFile,
            @RequestParam("conclusion") String conclusion,
            @PathVariable("patientId") Long patientId) {
        if (bilanFile == null || bilanFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Biology image file is empty!"));
        }
        biologyService.uploadBiologyData(bilanFile, conclusion, patientId);
        return ResponseEntity.ok(new MessageResponse("Biology saved successfully!"));
    }
}
