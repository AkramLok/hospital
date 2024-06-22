package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "room-assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roomtype roomType;
    private LocalDateTime assignmentDateTime;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference("patient-roomAssignments")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
