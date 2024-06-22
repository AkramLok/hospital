package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bed")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    @JsonBackReference("sector-beds")
    private Sector sector;

    @OneToOne(mappedBy = "bed", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonManagedReference("bed-patient")
    private Patient currentPatient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor assignedDoctor;

    private LocalDateTime startDateTime;

    @Enumerated(EnumType.STRING)
    private BedState state;

    @OneToMany(mappedBy = "bed", cascade = CascadeType.ALL)
    @JsonManagedReference("bed-bedAssignmentHistories")
    private List<BedAssignmentHistory> bedAssignmentHistories;
}
