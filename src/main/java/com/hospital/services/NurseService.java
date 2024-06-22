package com.hospital.services;

import com.hospital.entities.Nurse;
import com.hospital.entities.NurseShift;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NurseService {
    Nurse createNurse(Nurse nurse);

    List<Nurse> getAllNurses();

    Optional<Nurse> getNurseById(Long id);

    Nurse updateNurse(Long id, Nurse nurseDetails);

    void deleteNurse(Long id);

}
