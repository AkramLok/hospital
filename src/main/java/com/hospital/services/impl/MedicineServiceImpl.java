package com.hospital.services.impl;

import java.util.List;
import java.util.Optional;

import com.hospital.entities.Medicine;
import com.hospital.repositories.MedicineRepository;
import com.hospital.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedicineServiceImpl implements MedicineService{

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public Optional<Medicine> getMedicinesById(Long id) {
        return medicineRepository.findById(id);
    }
    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    @Override
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}
