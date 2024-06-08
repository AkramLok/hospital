package com.hospital.repositories;

import com.hospital.entities.Antecedent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntecedentRepository  extends JpaRepository<Antecedent, Long> {
}
