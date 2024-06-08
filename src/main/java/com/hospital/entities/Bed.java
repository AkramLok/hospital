package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @OneToOne(mappedBy = "bed", cascade = CascadeType.ALL)
    @JsonManagedReference("bed-patient")
    private Patient currentPatient;

    private LocalDateTime startDateTime;

    @Enumerated(EnumType.STRING)
    private BedState state;
}
