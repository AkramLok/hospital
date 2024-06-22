package com.hospital.services;

import com.hospital.entities.DoctorShift;

import java.time.LocalDate;
import java.util.List;

public interface DoctorShiftService {
    DoctorShift createShift(DoctorShift shift);

    void generateShifts(LocalDate startDate, LocalDate endDate);

    List<DoctorShift> getDoctorGuards(LocalDate startDate, LocalDate endDate, String shiftType);

    DoctorShift addShift(LocalDate date, String shiftType, Long doctorId);

    DoctorShift updateShift(Long shiftId, Long doctorId);

    void deleteShift(Long shiftId);
}
