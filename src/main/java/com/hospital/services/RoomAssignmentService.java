package com.hospital.services;

import com.hospital.entities.RoomAssignment;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomAssignmentService {

    RoomAssignment createAssignment(Long doctorId, Long patientId, RoomAssignment assignment);

    List<RoomAssignment> getAssignmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void deleteAssignment(Long id);
}
