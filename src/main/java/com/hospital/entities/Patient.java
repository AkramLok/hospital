package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private int age;

    private String ville;

    private String assurance;

    private String profession;

    private String referenceID;

    @OneToOne
    @JoinColumn(name = "bed_id")
    @JsonBackReference("bed-patient")
    private Bed bed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_dossier_id", referencedColumnName = "id")
    @JsonManagedReference("medical-dossier-patient")
    private MedicalDossier medicalDossier;
}
