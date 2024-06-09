package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientUpdateRequest {
    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private int age;

    @NotBlank
    private String ville;

    private String assurance;

    private String profession;

    private String referenceID;
}
