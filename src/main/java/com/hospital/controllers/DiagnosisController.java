package com.hospital.controllers;

import com.hospital.entities.Antecedent;
import com.hospital.entities.Diagnosis;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.AntecedentService;
import com.hospital.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> addDiagnosisToPatient(
            @PathVariable Long patientId,
            @RequestBody Diagnosis diagnosis) {
        diagnosisService.addDiagnosisToPatient(patientId, diagnosis);
        return ResponseEntity.ok(new MessageResponse("Diagnosis added to patient successfully!"));
    }
}
