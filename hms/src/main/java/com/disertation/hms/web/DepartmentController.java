package com.disertation.hms.web;

import com.disertation.hms.domain.DepartmentDetails;
import com.disertation.hms.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDetails addDepartment(@RequestBody DepartmentDetails departmentDetails) {
        log.info("Request to add department : {}", departmentDetails);
        try {
            DepartmentDetails addedDepartment = departmentService.addDepartment(departmentDetails);
            log.info("Department added : {}", addedDepartment);
            return addedDepartment;
        } catch (Exception e) {
            log.error("Error occurred while adding department : {}. Error: {}", departmentDetails, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public List<DepartmentDetails> getAllDepartments() {
        log.info("Request to get all departments");
        try {
            List<DepartmentDetails> departmentDetails = departmentService.getAllDepartments();
            log.info("Retrieved total {} departments", departmentDetails.size());
            return departmentDetails;
        } catch (Exception e) {
            log.error("Error occurred while getting all departments. Error : {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{departmentId}")
    public DepartmentDetails getDepartmentById(@PathVariable Long departmentId) {
        log.info("Request to get department : {}", departmentId);
        try {
            DepartmentDetails departmentDetails = departmentService.getDepartmentById(departmentId);
            log.info("Retrieved department : {}", departmentDetails);
            return departmentDetails;
        } catch (Exception e) {
            log.error("Error occurred while getting department for id : {}, error: {}", departmentId, e.getMessage());
            throw e;
        }
    }
}
