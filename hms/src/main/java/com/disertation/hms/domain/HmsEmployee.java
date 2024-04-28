package com.disertation.hms.domain;

import lombok.Data;

@Data
public class HmsEmployee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Long departmentId;
}
