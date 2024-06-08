package com.hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hospital.entities.MedicalDossier;

@Repository
public interface MedicalDossierRepository extends JpaRepository<MedicalDossier, Long> {
}
