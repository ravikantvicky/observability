package com.disertation.hms.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String email;
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
