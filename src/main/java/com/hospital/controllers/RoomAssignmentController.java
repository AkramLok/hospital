package com.hospital.controllers;

import com.hospital.entities.RoomAssignment;
import com.hospital.services.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/room-assignments")
public class RoomAssignmentController {

    @Autowired
    private RoomAssignmentService roomAssignmentService;

    @PostMapping("/doctor/{doctorId}/patient/{patientId}")
    public RoomAssignment createRoomAssignment(@PathVariable Long doctorId, @PathVariable Long patientId, @RequestBody RoomAssignment roomAssignment) {
        return roomAssignmentService.createAssignment(doctorId, patientId, roomAssignment);
    }

    @GetMapping("/by-date-range")
    public List<RoomAssignment> getAssignmentsByDateRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return roomAssignmentService.getAssignmentsByDateRange(start, end);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomAssignment(@PathVariable Long id) {
        roomAssignmentService.deleteAssignment(id);
    }

}
