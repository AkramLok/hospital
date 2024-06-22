package com.hospital.services.impl;

import com.hospital.entities.Doctor;
import com.hospital.entities.DoctorShift;
import com.hospital.repositories.DoctorRepository;
import com.hospital.repositories.DoctorShiftRepository;
import com.hospital.services.DoctorShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorShiftServiceImpl implements DoctorShiftService {

    @Autowired
    private DoctorShiftRepository doctorShiftRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorShift createShift(DoctorShift shift) {
        return doctorShiftRepository.save(shift);
    }
    @Override
    public void generateShifts(LocalDate startDate, LocalDate endDate) {
        List<Doctor> doctors = doctorRepository.findAll();
        int doctorCount = doctors.size();
        int doctorIndex = 0;

        // Iterate through the date range
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Check if a day shift exists for the current date
            DoctorShift existingDayShift = doctorShiftRepository.findByShiftDateAndShiftType(date, "jour");
            // Check if a night shift exists for the current date
            DoctorShift existingNightShift = doctorShiftRepository.findByShiftDateAndShiftType(date, "nuit");

            // Update existing day shift or create a new one
            DoctorShift dayShift = existingDayShift != null ? existingDayShift : new DoctorShift();
            dayShift.setShiftDate(date);
            dayShift.setShiftType("jour");
            dayShift.setDoctor(doctors.get(doctorIndex % doctorCount));
            doctorShiftRepository.save(dayShift);

            doctorIndex++;

            // Update existing night shift or create a new one
            DoctorShift nightShift = existingNightShift != null ? existingNightShift : new DoctorShift();
            nightShift.setShiftDate(date);
            nightShift.setShiftType("nuit");
            nightShift.setDoctor(doctors.get(doctorIndex % doctorCount));
            doctorShiftRepository.save(nightShift);

            doctorIndex++;
        }
    }


    @Override
    public List<DoctorShift> getDoctorGuards(LocalDate startDate, LocalDate endDate, String shiftType) {
        if (shiftType != null && !shiftType.isEmpty()) {
            return doctorShiftRepository.findByShiftDateBetweenAndShiftType(startDate, endDate, shiftType);
        } else {
            return doctorShiftRepository.findByShiftDateBetween(startDate, endDate);
        }
    }

    @Override
    public DoctorShift addShift(LocalDate date, String shiftType, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorShift shift = new DoctorShift();
        shift.setShiftDate(date);
        shift.setShiftType(shiftType);
        shift.setDoctor(doctor);

        return doctorShiftRepository.save(shift);
    }

    @Override
    public DoctorShift updateShift(Long shiftId, Long doctorId) {
        Optional<DoctorShift> existingShift = doctorShiftRepository.findById(shiftId);
        if (existingShift.isPresent()) {
            DoctorShift shift = existingShift.get();
            shift.setDoctor(doctorRepository.findById(doctorId).get());
            return doctorShiftRepository.save(shift);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteShift(Long shiftId) {
        doctorShiftRepository.deleteById(shiftId);
    }

}
