package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;
    private String specialty;
    private String phoneNumber;

    @OneToMany(mappedBy = "assignedDoctor")
    @JsonIgnore
    private List<Bed> beds;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<RoomAssignment> roomAssignments;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<DoctorShift> doctorShifts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom= nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    public List<DoctorShift> getDoctorShifts() {
        return doctorShifts;
    }

    public void setDoctorShifts(List<DoctorShift> doctorShifts) {
        this.doctorShifts = doctorShifts;
    }
}
