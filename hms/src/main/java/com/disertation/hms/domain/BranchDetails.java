package com.disertation.hms.domain;

import com.disertation.hms.entities.Branch;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class BranchDetails {
    private Long id;
    private String name;
    private String address;
    private List<DepartmentDetails> departments;

    public BranchDetails(Branch branch) {
        BeanUtils.copyProperties(branch, this);
    }

    public BranchDetails(Branch branch, List<DepartmentDetails> departments) {
        BeanUtils.copyProperties(branch, this);
        this.departments = departments;
    }

    public Branch toBranchEntity() {
        Branch branch = new Branch();
        BeanUtils.copyProperties(this, branch);
        return branch;
    }
}
