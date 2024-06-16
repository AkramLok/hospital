package com.hospital.controllers;

import com.hospital.entities.Patient;
import com.hospital.payload.response.MessageResponse;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            Patient savedPatient = patientService.addPatient(patient);
            return ResponseEntity.ok(savedPatient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Patient non enregistré, erreur " + e.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        try {
            Patient updatedpatient = patientService.updatePatient(id, updatedPatient);
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Patient non mis à jour, erreur  "+e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok(new MessageResponse("Patient et données associées supprimés avec succès !"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(new MessageResponse("Erreur lors de la suppression du patient : " + e.getMessage()));
        }
    }

    @GetMapping("/without-bed")
    public List<Patient> getAllPatientsWithoutBed() {
        return patientRepository.findAllWithoutBed();
    }
}
