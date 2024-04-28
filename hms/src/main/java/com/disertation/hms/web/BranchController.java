package com.disertation.hms.web;

import com.disertation.hms.domain.BranchDetails;
import com.disertation.hms.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    private final Logger log = LoggerFactory.getLogger(BranchController.class);

    private final BranchService branchService;
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public BranchDetails createBranch(@RequestBody BranchDetails branchDetails) {
        log.info("Request to create a new branch with {}", branchDetails);
        try {
            BranchDetails createdBranch = branchService.createBranch(branchDetails);
            log.info("Created a new branch with {}", createdBranch);
            return createdBranch;
        } catch (Exception e) {
            log.error("Error creating a new branch with {}, error: {}", branchDetails, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public List<BranchDetails> getAllBranches() {
        log.info("Request to get all branches");
        try {
            List<BranchDetails> branches = branchService.getAllBranches();
            log.info("Got total {} branches.", branches.size());
            return branches;
        } catch (Exception e) {
            log.error("Error getting all branches. Error: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{branchId}")
    public BranchDetails getBranchById(@PathVariable("branchId") Long id) {
        log.info("Request to get Branch with id {}", id);
        try {
            BranchDetails branchDetails = branchService.findBranchById(id);
            log.info("Branch Details retrieved successfully for branchId: {}", id);
            return branchDetails;
        } catch (Exception e) {
            log.error("Error getting Branch with id {}, error: {}", id, e.getMessage());
            throw e;
        }
    }
}
