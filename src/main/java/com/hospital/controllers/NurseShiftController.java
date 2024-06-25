package com.hospital.controllers;

import com.hospital.entities.Nurse;
import com.hospital.entities.NurseShift;
import com.hospital.services.NurseService;
import com.hospital.services.NurseShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nurse-shifts")
public class NurseShiftController {

    @Autowired
    private NurseShiftService nurseShiftService;

    @PostMapping("/assign")
    public NurseShift createNurseShift(@RequestBody NurseShift shift) {
        return nurseShiftService.createShift(shift);
    }

    @PostMapping("/generate")
    public void generateShifts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        nurseShiftService.generateShifts(startDate, endDate);
    }

    @DeleteMapping("/delete")
    public void deleteShifts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        nurseShiftService.deleteShifts(startDate, endDate);
    }

    @GetMapping("/guards")
    public List<NurseShift> getNurseShifts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String shiftType
    ) {
        return nurseShiftService.getNurseShifts(startDate, endDate, shiftType);
    }

    @PostMapping("/date/{date}/shift-type/{shiftType}/nurse/{nurseId}")
    public NurseShift addShift(
            @PathVariable LocalDate date,
            @PathVariable String shiftType,
            @PathVariable Long nurseId) {
        return nurseShiftService.addShift(date, shiftType, nurseId);
    }

    @PutMapping("/{shiftId}/nurse/{nurseId}")
    public NurseShift updateShift(@PathVariable Long shiftId, @PathVariable Long nurseId) {
        return nurseShiftService.updateShift(shiftId, nurseId);
    }

    @DeleteMapping("/{shiftId}")
    public void deleteShift(@PathVariable Long shiftId) {
        nurseShiftService.deleteShift(shiftId);
    }
}
