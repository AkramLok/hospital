package com.hospital.services.impl;

import com.hospital.entities.Doctor;
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
    public List<Doctor> getAllActiveDoctors() {
        return doctorRepository.findByActive(true);
    }

    @Override
    public List<Doctor> getAllNonActiveDoctors() {
        return doctorRepository.findByActive(false);
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

    @Override
    public void deactivateDoctor(Long id) {
        doctorRepository.deactivateDoctor(id);
    }

    @Override
    public void activateDoctor(Long id) {
        doctorRepository.activateDoctor(id);
    }

}
