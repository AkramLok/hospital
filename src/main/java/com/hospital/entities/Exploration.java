package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exploration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exploration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ExplorationType type;
    private String imageUrl;
    @Column(length = 1000)
    private String conclusion;

    @ManyToOne
    @JoinColumn(name = "medical_dossier_id", nullable = false)
    @JsonBackReference("medical-dossier-exploration")
    private MedicalDossier medicalDossier;

}

