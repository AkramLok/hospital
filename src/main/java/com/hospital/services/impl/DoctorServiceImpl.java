package com.hospital.services.impl;

import com.hospital.entities.Doctor;
import com.hospital.entities.User;
import com.hospital.repositories.DoctorRepository;
import com.hospital.repositories.UserRepository;
import com.hospital.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

}
