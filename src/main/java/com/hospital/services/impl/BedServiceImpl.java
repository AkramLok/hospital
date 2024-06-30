package com.hospital.services.impl;

import com.hospital.entities.*;
import com.hospital.repositories.*;
import com.hospital.services.BedService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private BedAssignmentHistoryRepository bedAssignmentHistoryRepository;

    @Override
    public Bed assignPatientToBed(Long bedId, Long patientId, Long doctorId) {
        Optional<Bed> bedOptional = bedRepository.findById(bedId);
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (!bedOptional.isPresent()) {
            throw new RuntimeException("Bed not found");
        }

        if (!patientOptional.isPresent()) {
            throw new RuntimeException("Patient not found");
        }

        if (!doctorOptional.isPresent()) {
            throw new RuntimeException("Doctor not found");
        }

        Bed bed = bedOptional.get();
        Patient patient = patientOptional.get();
        Doctor doctor = doctorOptional.get();

        if (bed.getCurrentPatient() != null) {
            throw new RuntimeException("Bed is not empty");
        }

        if (patient.getBed() != null) {
            throw new RuntimeException("Patient is already assigned to another bed");
        }

        bed.setCurrentPatient(patient);
        bed.setAssignedDoctor(doctor);
        patient.setBed(bed);
        bed.setStartDateTime(LocalDateTime.now());
        bed.setState(BedState.OCCUPIED);

        BedAssignmentHistory history = new BedAssignmentHistory();
        history.setBed(bed);
        history.setPatient(patient);
        history.setDoctorDetails(doctor.getPrenom()+" "+doctor.getNom()+"-"+doctor.getPhoneNumber()+"-"+doctor.getSpecialty()+"-"+doctor.getId());
        history.setStartDateTime(LocalDateTime.now());
        history.setStatus(PatientStatus.ASSIGNED);
        history.setBedAssignedId(bed.getId());
        bedAssignmentHistoryRepository.save(history);

        return bedRepository.save(bed);
    }

    @Override
    public Bed assignPatientToBed(Long bedId, Long patientId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteBed(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new IllegalArgumentException("Bed not found"));
        if (bed.getCurrentPatient() != null) {
            bed.getCurrentPatient().setBed(null);
        }

        bedRepository.delete(bed);
    }

    @Override
    public Bed removePatientFromBed(Long bedId) {
        Optional<Bed> bedOptional = bedRepository.findById(bedId);

        if (!bedOptional.isPresent()) {
            throw new RuntimeException("Bed not found");
        }

        Bed bed = bedOptional.get();
        Patient patient = bed.getCurrentPatient();

        if (patient == null) {
            throw new RuntimeException("No patient assigned to the bed");
        }

        // Remove the patient from the bed and update the state
        patient.setBed(null);
        bed.setCurrentPatient(null);
        bed.setState(BedState.EMPTY);
        bed.setStartDateTime(null);

        // Update endDateTime and status in BedAssignmentHistory
        BedAssignmentHistory history = bedAssignmentHistoryRepository.findFirstByBedAndPatientOrderByStartDateTimeDesc(bed, patient);
        if (history != null) {
            history.setEndDateTime(LocalDateTime.now());
            history.setStatus(PatientStatus.UNASSIGNED_BUT_PREVIOUSLY_HOSPITALIZED);
            bedAssignmentHistoryRepository.save(history);
        }

        patientRepository.save(patient);
        return bedRepository.save(bed);
    }

    @Override
    public List<Bed> getBedsByPatientId(Long patientId) {
        return bedRepository.findByCurrentPatientId(patientId);
    }


    @Override
    public Bed updateBedState(Long bedId) {
        Optional<Bed> bedOptional = bedRepository.findById(bedId);

        if (bedOptional.isPresent()) {
            Bed bed = bedOptional.get();

            // Logic to determine the new state
            // For now, toggle between EMPTY and OCCUPIED as an example
            if (bed.getState() == BedState.OCCUPIED) {
                bed.setState(BedState.EMPTY);
            } else {
                bed.setState(BedState.OCCUPIED);
            }

            return bedRepository.save(bed);
        } else {
            throw new RuntimeException("Bed not found");
        }
    }

    @Override
    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    @Override
    public Bed getBedById(Long id) {
        return bedRepository.findById(id).orElseThrow(() -> new RuntimeException("Bed not found"));
    }

    @Override
    public List<Bed> getBedsBySectorId(Long sectorId) {
        return bedRepository.findBySectorId(sectorId);
    }


    @Override
    public Bed addBedToSector(Long sectorId) {
        Optional<Sector> sectorOptional = sectorRepository.findById(sectorId);

        if (sectorOptional.isPresent()) {
            Sector sector = sectorOptional.get();
            Bed bed = new Bed();
            bed.setSector(sector);
            bed.setState(BedState.EMPTY);
            return bedRepository.save(bed);
        } else {
            throw new RuntimeException("Sector not found");
        }
    }

}
