package com.hospital.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hospital.entities.Medicine;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineService.getMedicinesById(id);
        return medicine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medicine createMedicine(@RequestBody Medicine medicine) {
        return medicineService.saveMedicine(medicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicineDetails) {
        Optional<Medicine> medicine = medicineService.getMedicinesById(id);
        if (medicine.isPresent()) {
            Medicine updatedMedicine = medicine.get();
            updatedMedicine.setName(medicineDetails.getName());
            updatedMedicine.setQuantity(medicineDetails.getQuantity());
            return ResponseEntity.ok(medicineService.saveMedicine(updatedMedicine));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        if (medicineService.getMedicinesById(id).isPresent()) {
            medicineService.deleteMedicine(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity<Medicine> addQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Optional<Medicine> medicine = medicineService.getMedicinesById(id);
        if (medicine.isPresent()) {
            Medicine updatedMedicine = medicine.get();
            updatedMedicine.setQuantity(updatedMedicine.getQuantity() + body.get("quantity"));
            return ResponseEntity.ok(medicineService.saveMedicine(updatedMedicine));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/subtract")
    public ResponseEntity<?> subtractQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Optional<Medicine> medicine = medicineService.getMedicinesById(id);
        if (medicine.isPresent()) {
            Medicine updatedMedicine = medicine.get();
            int quantity = body.get("quantity");
            int currentQuantity = updatedMedicine.getQuantity();

            if (currentQuantity < quantity) {
                return ResponseEntity.badRequest().body(new MessageResponse("Quantité insuffisante pour soustraire."));
            }

            updatedMedicine.setQuantity(currentQuantity - quantity);
            return ResponseEntity.ok(medicineService.saveMedicine(updatedMedicine));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
