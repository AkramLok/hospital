package com.hospital.controllers;

import com.hospital.entities.Antecedent;
import com.hospital.entities.Diagnosis;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.AntecedentService;
import com.hospital.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(new MessageResponse("Le diagnostic a été ajouté au patient avec succès !"));
    }

    @GetMapping("/medical-dossier/{medicalDossierId}")
    public ResponseEntity<List<Diagnosis>> getAllDiagnosesByMedicalDossierId(@PathVariable Long medicalDossierId) {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnosesByMedicalDossierId(medicalDossierId);
        return ResponseEntity.ok(diagnoses);
    }
}
