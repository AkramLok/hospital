package com.hospital.services.impl;

import com.hospital.entities.Nurse;
import com.hospital.repositories.NurseRepository;
import com.hospital.services.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    @Override
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    @Override
    public Optional<Nurse> getNurseById(Long id) {
        return nurseRepository.findById(id);
    }

    @Override
    public Nurse updateNurse(Long id, Nurse nurseDetails) {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(() -> new RuntimeException("Nurse not found with id " + id));
        nurse.setNom(nurseDetails.getNom());
        nurse.setPrenom(nurseDetails.getPrenom());
        nurse.setSpecialty(nurseDetails.getSpecialty());
        nurse.setPhoneNumber(nurseDetails.getPhoneNumber());
        return nurseRepository.save(nurse);
    }

    @Override
    public void deleteNurse(Long id) {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(() -> new RuntimeException("Nurse not found with id " + id));
        nurseRepository.delete(nurse);
    }
}
