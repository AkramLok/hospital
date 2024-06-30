package com.hospital.services.impl;

import com.hospital.entities.RoomAssignment;
import com.hospital.entities.Doctor;
import com.hospital.entities.Patient;
import com.hospital.repositories.RoomAssignmentRepository;
import com.hospital.repositories.DoctorRepository;
import com.hospital.repositories.PatientRepository;
import com.hospital.services.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomAssignmentServiceImpl implements RoomAssignmentService {

    @Autowired
    private RoomAssignmentRepository roomAssignmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public RoomAssignment createAssignment(Long doctorId, Long patientId, RoomAssignment assignment) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient ID"));

        assignment.setDoctor(doctor);
        assignment.setPatient(patient);

        if (assignment.getAssignmentDateTime() == null) {
            assignment.setAssignmentDateTime(LocalDateTime.now());
        }

        return roomAssignmentRepository.save(assignment);
    }

    @Override
    public List<RoomAssignment> getAssignmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return roomAssignmentRepository.findByAssignmentDateTimeBetween(startDate, endDate);
    }

    @Override
    public void deleteAssignment(Long id) {
        roomAssignmentRepository.deleteById(id);
    }
}
