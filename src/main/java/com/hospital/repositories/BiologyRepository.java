package com.hospital.repositories;

import com.hospital.entities.Biology;
import com.hospital.entities.ECG;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiologyRepository extends JpaRepository<Biology, Long> {
}
