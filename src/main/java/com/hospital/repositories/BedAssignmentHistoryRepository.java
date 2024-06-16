package com.hospital.repositories;

import com.hospital.entities.Bed;
import com.hospital.entities.BedAssignmentHistory;
import com.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedAssignmentHistoryRepository extends JpaRepository<BedAssignmentHistory, Long> {
    BedAssignmentHistory findFirstByBedAndPatientOrderByStartDateTimeDesc(Bed bed, Patient patient);
}
