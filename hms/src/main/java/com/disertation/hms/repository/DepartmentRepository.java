package com.disertation.hms.repository;

import com.disertation.hms.domain.DepartmentDetails;
import com.disertation.hms.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByBranchId(Long branchId);
}
