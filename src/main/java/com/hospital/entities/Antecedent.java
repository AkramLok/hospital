package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "antecedent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Antecedent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String personal;
    @Column(length = 1000)
    private String familial;
    @Column(length = 1000)
    private String cardiovascularRiskFactors;

    @ManyToOne
    @JoinColumn(name = "medical_dossier_id", nullable = false)
    @JsonBackReference("medical-dossier-antecedent")
    private MedicalDossier medicalDossier;

}
