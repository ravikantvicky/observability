package com.disertation.hms.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String name;
    private String address;
}
