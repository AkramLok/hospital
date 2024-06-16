package com.hospital.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "medicament")
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

}
