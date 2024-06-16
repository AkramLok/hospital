package com.hospital.controllers;

import com.hospital.entities.Antecedent;
import com.hospital.entities.ClinicalExam;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.AntecedentService;
import com.hospital.services.ClinicalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinical-exams")
public class ClinicalExamController {

    @Autowired
    private ClinicalExamService clinicalExamService;

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> addAntecedentToPatient(
            @PathVariable Long patientId,
            @RequestBody ClinicalExam clinicalExam) {
        clinicalExamService.addClinicalExamToPatient(patientId, clinicalExam);
        return ResponseEntity.ok(new MessageResponse(" a été ajouté au patient avec succès !"));
    }

    @GetMapping("/section/{section}/medical-dossier/{medicalDossierId}")
    public List<ClinicalExam> getClinicalExamsBySection(@PathVariable String section, @PathVariable Long medicalDossierId) {
        return clinicalExamService.getClinicalExamsBySection(section, medicalDossierId);
    }
}
