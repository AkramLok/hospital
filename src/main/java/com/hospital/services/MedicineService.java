package com.hospital.services;

import com.hospital.entities.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> getAllMedicines();

    Optional<Medicine> getMedicinesById(Long id);

    Medicine saveMedicine(Medicine medicine);

    void deleteMedicine(Long id);
}
