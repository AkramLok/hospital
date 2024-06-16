package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diagnosis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String diagnosis;
    @Column(length = 1000)
    private String diagnosisDifferentiel;

    @ManyToOne
    @JoinColumn(name = "medical_dossier_id", nullable = false)
    @JsonBackReference("medical-dossier-diagnosis")
    private MedicalDossier medicalDossier;

}
