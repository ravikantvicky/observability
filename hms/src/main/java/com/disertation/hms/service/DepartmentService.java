package com.disertation.hms.service;

import com.disertation.hms.domain.DepartmentDetails;
import com.disertation.hms.domain.HmsEmployee;
import com.disertation.hms.exception.HmsNotFoundException;
import com.disertation.hms.exception.InvalidRequestException;
import com.disertation.hms.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentService {
    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;
    private final RestTemplate restTemplate;

    public DepartmentService(DepartmentRepository departmentRepository, RestTemplate restTemplate) {
        this.departmentRepository = departmentRepository;
        this.restTemplate = restTemplate;
    }

    public DepartmentDetails addDepartment(DepartmentDetails departmentDetails) {
        if (departmentDetails == null || departmentDetails.getName() == null) {
            throw new InvalidRequestException("Invalid request data. Department name cannot be null.");
        }
        if (departmentDetails.getId() != null && departmentRepository.findById(departmentDetails.getId()).isPresent()) {
            throw new InvalidRequestException("Department already exists with id: " + departmentDetails.getId());
        }
        return new DepartmentDetails(departmentRepository.save(departmentDetails.toDepartmentEntity()));
    }

    public List<DepartmentDetails> getAllDepartments() {
        return departmentRepository.findAll().stream().map(DepartmentDetails::new).toList();
    }

    public List<DepartmentDetails> getAllDepartmentsByBranchId(Long branchId) {
        if (branchId == null) {
            throw new InvalidRequestException("Invalid Branch id.");
        }

        return departmentRepository.findByBranchId(branchId).stream().map(DepartmentDetails::new).toList();
    }

    public DepartmentDetails getDepartmentById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Invalid request data. Department id cannot be null.");
        }

        DepartmentDetails departmentDetails = departmentRepository.findById(id)
                .map(DepartmentDetails::new)
                .orElseThrow(() -> new HmsNotFoundException("Department not found with id: " + id));

        // Fetch employees
        log.info("Fetching Employee details for department: {}", departmentDetails.getName());
        String url = "http://hms-hr:8090/employee/department/" + id;
        ResponseEntity<List<HmsEmployee>> employeesResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        log.info("Fetching Employee details completed for department: {}, HTTP status: {}",
                departmentDetails.getName(), employeesResponse.getStatusCode());
        if (employeesResponse.getStatusCode().is2xxSuccessful() && employeesResponse.getBody() != null) {
            log.info("Retrieved {} employees for department: {}",
                    employeesResponse.getBody().size(),
                    departmentDetails.getName());
            departmentDetails.setEmployees(employeesResponse.getBody());
        }
        return departmentDetails;
    }


}
