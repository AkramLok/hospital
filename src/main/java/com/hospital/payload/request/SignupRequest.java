package com.hospital.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  private String doctorNom;

  @NotBlank
  private String doctorPrenom;

  @NotBlank
  private String specialty;

  @NotBlank
  @Size(min = 10, max = 15)
  private String phoneNumber;

  // Getters and setters

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDoctorNom() {
    return doctorNom;
  }

  public void setDoctorNom(String doctorNom) {
    this.doctorNom = doctorNom;
  }

  public String getDoctorPrenom() {
    return doctorPrenom;
  }

  public void setDoctorPrenom(String doctorPrenom) {
    this.doctorPrenom = doctorPrenom;
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
}
