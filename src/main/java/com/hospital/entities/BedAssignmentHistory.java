package com.hospital.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bed_assignment_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedAssignmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id", nullable = false)
    @JsonBackReference("bed-bedAssignmentHistories")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference("patient-bedAssignmentHistories")
    private Patient patient;

    private String doctorDetails;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private PatientStatus status;

    @Column(name = "bed_assigned_id")
    private Long bedAssignedId;
}
