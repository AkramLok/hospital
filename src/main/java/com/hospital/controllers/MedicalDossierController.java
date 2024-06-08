package com.hospital.controllers;

import com.hospital.entities.MedicalDossier;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.MedicalDossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-dossiers")
public class MedicalDossierController {

    @Autowired
    private MedicalDossierService medicalDossierService;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<MedicalDossier> addMedicalDossier(
            @PathVariable Long patientId,
            @RequestBody MedicalDossier medicalDossier) {
        MedicalDossier addedMedicalDossier = medicalDossierService.addMedicalDossier(patientId, medicalDossier);
        return ResponseEntity.ok(addedMedicalDossier);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalDossier> getMedicalDossier(@PathVariable Long patientId) {
        MedicalDossier medicalDossier = medicalDossierService.getMedicalDossier(patientId);
        return ResponseEntity.ok(medicalDossier);
    }

    @PatchMapping("/patient/{patientId}")
    public ResponseEntity<?> updateMedicalDossier(
            @PathVariable Long patientId,
            @RequestBody MedicalDossier partialMedicalDossier) {
        try
        {
            MedicalDossier updatedMedicalDossier = medicalDossierService.updateMedicalDossier(patientId, partialMedicalDossier);
            return ResponseEntity.ok(new MessageResponse("Medical dossier patched successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error in patching medical dossier."));
        }
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteMedicalDossier(@PathVariable Long patientId) {
        medicalDossierService.deleteMedicalDossier(patientId);
        return ResponseEntity.noContent().build();
    }
}
