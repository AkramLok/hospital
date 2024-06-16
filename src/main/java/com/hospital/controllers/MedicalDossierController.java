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

    @PutMapping("/patient/{patientId}")
    public ResponseEntity<?> updateMedicalDossier(
            @PathVariable Long patientId,
            @RequestBody MedicalDossier partialMedicalDossier) {
        try {
            MedicalDossier updatedMedicalDossier = medicalDossierService.updateMedicalDossier(patientId, partialMedicalDossier);
            return ResponseEntity.ok(new MessageResponse(" a été mis à jour avec succès !"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Une erreur est survenue lors de la mise à jour du dossier médical."));
        }
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteMedicalDossier(@PathVariable Long patientId) {
        medicalDossierService.deleteMedicalDossier(patientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<MedicalDossier> archiveDossier(@PathVariable Long id) {
        MedicalDossier archivedDossier = medicalDossierService.archiveDossier(id);
        return ResponseEntity.ok(archivedDossier);
    }

    @PostMapping("/{id}/unarchive")
    public ResponseEntity<MedicalDossier> unarchiveDossier(@PathVariable Long id) {
        MedicalDossier unarchivedDossier = medicalDossierService.unarchiveDossier(id);
        return ResponseEntity.ok(unarchivedDossier);
    }
}
