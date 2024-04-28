package com.disertation.hms.domain;

import com.disertation.hms.entities.Branch;
import com.disertation.hms.entities.Department;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDetails {
    private Long id;
    private Long branchId;
    private String name;
    private String description;
    private String phoneNumber;
    private String email;
    private List<HmsEmployee> employees;

    public DepartmentDetails(Department department) {
        BeanUtils.copyProperties(department, this);
        this.branchId = department.getBranch().getId();
    }

    public Department toDepartmentEntity() {
        Department department = new Department();
        BeanUtils.copyProperties(this, department);
        Branch branch = new Branch();
        branch.setId(branchId);
        department.setBranch(branch);
        return department;
    }
}
