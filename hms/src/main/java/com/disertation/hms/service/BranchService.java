package com.disertation.hms.service;

import com.disertation.hms.domain.BranchDetails;
import com.disertation.hms.entities.Branch;
import com.disertation.hms.exception.HmsNotFoundException;
import com.disertation.hms.exception.InvalidRequestException;
import com.disertation.hms.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final DepartmentService departmentService;

    public BranchService(BranchRepository branchRepository, DepartmentService departmentService) {
        this.branchRepository = branchRepository;
        this.departmentService = departmentService;
    }

    public BranchDetails createBranch(BranchDetails branchDetails) {
        if (branchDetails == null || !StringUtils.hasText(branchDetails.getName()) || !StringUtils.hasText(branchDetails.getAddress())) {
            throw new InvalidRequestException("Invalid request data. Please provide a valid branch name and address.");
        }
        if (branchDetails.getId() != null && branchRepository.findById(branchDetails.getId()).isPresent()) {
            throw new InvalidRequestException("Branch already exists with id: " + branchDetails.getId());
        }
        return new BranchDetails(branchRepository.save(branchDetails.toBranchEntity()));
    }

    public List<BranchDetails> getAllBranches() {
        return branchRepository.findAll().stream().map(BranchDetails::new).toList();
    }

    public BranchDetails findBranchById(Long id) {
        return branchRepository.findById(id)
                .map(this::branchWithDepartments)
                .orElseThrow(() -> new HmsNotFoundException("Branch not found with id: " + id));
    }

    private BranchDetails branchWithDepartments(Branch branch) {
        return new BranchDetails(branch, departmentService.getAllDepartmentsByBranchId(branch.getId()));
    }
}
