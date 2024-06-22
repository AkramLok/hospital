package com.hospital.repositories;

import com.hospital.entities.DoctorShift;
import com.hospital.entities.Nurse;
import com.hospital.entities.NurseShift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NurseShiftRepository extends JpaRepository<NurseShift, Long> {
    List<NurseShift> findByShiftDateBetweenAndShiftType(LocalDate startDate, LocalDate endDate, String shiftType);
    List<NurseShift> findByShiftDateBetween(LocalDate startDate, LocalDate endDate);
    NurseShift findByShiftDateAndShiftType(LocalDate date, String shiftType);
    NurseShift findByShiftDateAndShiftTypeAndNurse(LocalDate shiftDate, String shiftType, Nurse nurse);

}
