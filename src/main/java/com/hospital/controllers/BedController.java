package com.hospital.controllers;

import com.hospital.entities.Bed;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping("/assign/bed/{bedId}/patient/{patientId}")
    public ResponseEntity<?> assignPatientToBed(@PathVariable Long bedId, @PathVariable Long patientId) {
        try {
            Bed bed = bedService.assignPatientToBed(bedId, patientId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Exception : "+ e.getMessage()));
        }
    }

    @PostMapping("/remove/{bedId}")
    public ResponseEntity<?> removePatientFromBed(@PathVariable Long bedId) {
        try {
            Bed bed = bedService.removePatientFromBed(bedId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Exception : "+ e.getMessage()));
        }
    }

    @PutMapping("/update/{bedId}")
    public ResponseEntity<Bed> updateBedState(@PathVariable Long bedId) {
        try {
            Bed bed = bedService.updateBedState(bedId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Bed>> getAllBeds() {
        List<Bed> beds = bedService.getAllBeds();
        return ResponseEntity.ok(beds);
    }

    @GetMapping("/sector/{sectorId}")
    public ResponseEntity<List<Bed>> getBedsBySectorId(@PathVariable Long sectorId) {
        try {
            List<Bed> beds = bedService.getBedsBySectorId(sectorId);
            return ResponseEntity.ok(beds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bed> getBedById(@PathVariable Long id) {
        try {
            Bed bed = bedService.getBedById(id);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add/{sectorId}")
    public ResponseEntity<Bed> addBed(@PathVariable Long sectorId) {
        try {
            Bed savedBed = bedService.addBedToSector(sectorId);
            return ResponseEntity.ok(savedBed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

