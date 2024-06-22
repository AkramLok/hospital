package com.hospital.controllers;

import com.hospital.entities.Bed;
import com.hospital.payload.response.MessageResponse;
import com.hospital.services.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping("/assign/bed/{bedId}/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<?> assignPatientToBed(@PathVariable Long bedId, @PathVariable Long patientId, @PathVariable Long doctorId) {
        try {
            Bed bed = bedService.assignPatientToBed(bedId, patientId, doctorId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Exception : "+ e.getMessage()));
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bed>> getBedsByPatientId(@PathVariable Long patientId) {
        try {
            List<Bed> beds = bedService.getBedsByPatientId(patientId);
            return new ResponseEntity<>(beds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @DeleteMapping("/delete/{bedId}")
    public ResponseEntity<?> deleteBed(@PathVariable Long bedId) {
        try {
            bedService.deleteBed(bedId);
            return ResponseEntity.ok(new MessageResponse("Le lit a été supprimé avec succès."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Exception : "+ e.getMessage()));
        }
    }
}

