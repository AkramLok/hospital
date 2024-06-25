package com.hospital.repositories;

import com.hospital.entities.DoctorShift;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.List;

public interface DoctorShiftRepository extends JpaRepository<DoctorShift, Long> {
    List<DoctorShift> findByShiftDateBetweenAndShiftType(LocalDate startDate, LocalDate endDate, String shiftType);
    List<DoctorShift> findByShiftDateBetween(LocalDate startDate, LocalDate endDate);
    DoctorShift findByShiftDateAndShiftType(LocalDate date, String shiftType);
    @Modifying
    @Transactional
    void deleteByShiftDateBetween(LocalDate startDate, LocalDate endDate);

}
