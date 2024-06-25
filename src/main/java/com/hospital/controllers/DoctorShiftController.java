package com.hospital.controllers;

import com.hospital.entities.DoctorShift;
import com.hospital.services.DoctorShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-shifts")
public class DoctorShiftController {
    @Autowired
    private DoctorShiftService doctorShiftService;

    @PostMapping("/assign")
    public DoctorShift createDoctorShift(@RequestBody DoctorShift shift) {
        return doctorShiftService.createShift(shift);
    }

    @PostMapping("/generate")
    public void generateShifts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        doctorShiftService.generateShifts(startDate, endDate);
    }

    @DeleteMapping("/delete")
    public void deleteShifts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        doctorShiftService.deleteShifts(startDate, endDate);
    }


    @GetMapping("/guards")
    public List<DoctorShift> getDoctorGuards(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String shiftType
    ) {
        return doctorShiftService.getDoctorGuards(startDate, endDate, shiftType);
    }

    @PostMapping("/date/{date}/shift-type/{shiftType}/doctor/{doctorId}")
    public DoctorShift addShift(
            @PathVariable LocalDate date,
            @PathVariable String shiftType,
            @PathVariable Long doctorId) {
        return doctorShiftService.addShift(date, shiftType, doctorId);
    }

    @PutMapping("/{shiftId}/doctor/{doctorId}")
    public DoctorShift updateShift(@PathVariable Long shiftId, @PathVariable Long doctorId) {
        return doctorShiftService.updateShift(shiftId, doctorId);
    }

    @DeleteMapping("/{shiftId}")
    public void deleteShift(@PathVariable Long shiftId) {
        doctorShiftService.deleteShift(shiftId);
    }

}
