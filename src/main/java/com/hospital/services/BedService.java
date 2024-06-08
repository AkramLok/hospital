package com.hospital.services;

import com.hospital.entities.Bed;
import java.util.List;

public interface BedService {
    Bed assignPatientToBed(Long bedId, Long patientId);
    Bed removePatientFromBed(Long bedId);
    Bed updateBedState(Long bedId);
    List<Bed> getAllBeds();
    Bed getBedById(Long id);
    Bed addBedToSector(Long sectorId);
    List<Bed> getBedsBySectorId(Long sectorId);


}
