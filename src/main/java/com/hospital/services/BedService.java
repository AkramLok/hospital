package com.hospital.services;

import com.hospital.entities.Bed;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BedService {
    Bed assignPatientToBed(Long bedId, Long patientId, Long doctorId);

    Bed assignPatientToBed(Long bedId, Long patientId);

    @Transactional
    void deleteBed(Long bedId);

    Bed removePatientFromBed(Long bedId);

    List<Bed> getBedsByPatientId(Long patientId);

    Bed updateBedState(Long bedId);
    List<Bed> getAllBeds();
    Bed getBedById(Long id);
    Bed addBedToSector(Long sectorId);
    List<Bed> getBedsBySectorId(Long sectorId);


}
