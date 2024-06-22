package com.hospital.repositories;

import com.hospital.entities.RoomAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, Long> {
    List<RoomAssignment> findByAssignmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
