package com.hospital.repositories;

import com.hospital.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findBySectorId(Long sectorId);
    List<Bed> findByCurrentPatientId(Long patientId);
}
