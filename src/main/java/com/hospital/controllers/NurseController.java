package com.hospital.controllers;

import com.hospital.entities.Nurse;
import com.hospital.services.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @PostMapping
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return nurseService.createNurse(nurse);
    }

    @GetMapping
    public List<Nurse> getAllNurses() {
        return nurseService.getAllNurses();
    }

    @GetMapping("/{id}")
    public Optional<Nurse> getNurseById(@PathVariable Long id) {
        return nurseService.getNurseById(id);
    }

    @PutMapping("/{id}")
    public Nurse updateNurse(@PathVariable Long id, @RequestBody Nurse nurseDetails) {
        return nurseService.updateNurse(id, nurseDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteNurse(@PathVariable Long id) {
        nurseService.deleteNurse(id);
    }
}
