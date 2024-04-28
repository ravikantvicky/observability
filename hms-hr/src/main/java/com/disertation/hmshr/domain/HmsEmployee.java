package com.disertation.hmshr.domain;

import com.disertation.hmshr.entities.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class HmsEmployee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private Long departmentId;

    public HmsEmployee(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }

    public Employee toEmployeeEntity() {
        Employee employee = new Employee();
        BeanUtils.copyProperties(this, employee);
        return employee;
    }
}
