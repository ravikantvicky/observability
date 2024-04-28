package com.disertation.hmshr.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "hr")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Long departmentId;
}
