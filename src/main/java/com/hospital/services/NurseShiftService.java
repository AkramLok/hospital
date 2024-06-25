package com.hospital.services;

import com.hospital.entities.Nurse;
import com.hospital.entities.NurseShift;

import java.time.LocalDate;
import java.util.List;

public interface NurseShiftService {
    NurseShift createShift(NurseShift shift);

    void deleteShifts(LocalDate startDate, LocalDate endDate);

    void generateShifts(LocalDate startDate, LocalDate endDate);

    List<NurseShift> getNurseShifts(LocalDate startDate, LocalDate endDate, String shiftType);

    NurseShift addShift(LocalDate date, String shiftType, Long nurseId);

    NurseShift updateShift(Long shiftId, Long nurseId);

    void deleteShift(Long shiftId);
}
