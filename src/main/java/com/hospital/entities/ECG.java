package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ecg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ECG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    @Column(length = 1000)
    private String conclusion;

    @ManyToOne
    @JoinColumn(name = "medical_dossier_id", nullable = false)
    @JsonBackReference("medical-dossier-ecg")
    private MedicalDossier medicalDossier;

}
