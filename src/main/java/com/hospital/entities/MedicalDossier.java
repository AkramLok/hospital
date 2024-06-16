package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Medical_Dossier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "medicalDossier")
    @JsonBackReference("medical-dossier-patient")
    private Patient patient;

    @Column(length = 1000)
    private String hospitalization;
    @Column(length = 1000)
    private String historyDisease;
    @Column(length = 1000)
    private String primaryConclusion;
    @Column(length = 1000)
    private String conclusion;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-antecedent")
    private List<Antecedent> antecedents;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-clicincal-exam")
    private List<ClinicalExam> clinicalExams;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-ecg")
    private List<ECG> ecgs;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-diagnosis")
    private List<Diagnosis> diagnoses;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-exploration")
    private List<Exploration> explorations;

    @OneToMany(mappedBy = "medicalDossier", cascade = CascadeType.ALL)
    @JsonManagedReference("medical-dossier-biology")
    private List<Biology> biologies;

    // New attribute for archiving
    private boolean archived = false;

    /*
    //Pour diagnostic
    private String diagnosis;
    private String differentialDiagnosis;

    //Pour examen clinique
    private String cardiovascular;
    private String pulmonary;
    private String abdominal;
    private String general;
    private String functionalSigns;
    private String physicalSigns;

    //Pour antécédents
    private String personal;
    private String familial;
    private String cardiovascularRiskFactors;

    //Pour ECG
    private String imageECG;
    private String conclusionECG;

    //Pour exploration type radio de thorax
    private String imageRT;
    private String conclusionRT;

    //Pour exploration type echocardiographie
    private String imageEchoCardGraphie;
    private String conclusionEchoCardGraphie;

    //Pour biologie
    private String imageBilanBiology;
    private String conclusionBiology;
    */
}
