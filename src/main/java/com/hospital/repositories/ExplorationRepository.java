package com.hospital.repositories;

import com.hospital.entities.ECG;
import com.hospital.entities.Exploration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExplorationRepository extends JpaRepository<Exploration, Long> {
}
