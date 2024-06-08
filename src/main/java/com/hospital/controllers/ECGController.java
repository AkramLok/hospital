package com.hospital.controllers;

import com.hospital.payload.response.MessageResponse;
import com.hospital.services.ECGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ecgs")
public class ECGController {

    @Autowired
    ECGService ecgService;
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> uploadECGImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("conclusion") String conclusion,
            @PathVariable("patientId") Long patientId) {
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("ECG image file is empty!"));
        }
        ecgService.uploadECGData(imageFile, conclusion, patientId);
        return ResponseEntity.ok(new MessageResponse("ECG saved successfully!"));
    }
}
