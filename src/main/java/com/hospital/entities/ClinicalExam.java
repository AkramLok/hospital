package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clinical examen")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicalExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pulmonary;
    private String abdominal;
    private String generalExam;
    private String functionalSigns;
    private String physicalSigns;

    @ManyToOne
    @JoinColumn(name = "medical_dossier_id", nullable = false)
    @JsonBackReference("medical-dossier-clicincal-exam")
    private MedicalDossier medicalDossier;

}

