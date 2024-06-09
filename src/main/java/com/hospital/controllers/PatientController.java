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
            return ResponseEntity.ok(new MessageResponse("Patient saved successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Patient not saved, error "+e.getMessage()));
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
            Patient patient = patientService.updatePatient(id, updatedPatient);
            return ResponseEntity.ok(new MessageResponse("Patient updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Patient not updated, error "+e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok(new MessageResponse("Patient and associated data deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error deleting patient: " + e.getMessage()));
        }
    }

    @GetMapping("/without-bed")
    public List<Patient> getAllPatientsWithoutBed() {
        return patientRepository.findAllWithoutBed();
    }
}
