package com.disertation.hmshr.service;

import com.disertation.hmshr.domain.HmsEmployee;
import com.disertation.hmshr.exception.HmsNotFoundException;
import com.disertation.hmshr.exception.InvalidRequestException;
import com.disertation.hmshr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public HmsEmployee createEmployee(HmsEmployee employee) {
        if (employee == null || !StringUtils.hasText(employee.getFirstName())
                || !StringUtils.hasText(employee.getLastName())
                || ObjectUtils.isEmpty(employee.getDepartmentId())) {
            throw new InvalidRequestException("Invalid request data. Mandatory fields (firstName, lastName & departmentId) are missing.");
        }
        if (!ObjectUtils.isEmpty(employee.getId())
                && employeeRepository.existsById(employee.getId())) {
            throw new InvalidRequestException("Employee with id " + employee.getId() + " already exists");
        }
        return new HmsEmployee(employeeRepository.save(employee.toEmployeeEntity()));
    }

    public List<HmsEmployee> getAllEmployees() {
        return employeeRepository.findAll().stream().map(HmsEmployee::new).toList();
    }

    public HmsEmployee getEmployeeById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Employee id is null");
        }
        return employeeRepository.findById(id)
                .map(HmsEmployee::new)
                .orElseThrow(() -> new HmsNotFoundException("Employee not found with id: " + id));
    }

    public List<HmsEmployee> getEmployeesByDepartmentId(Long departmentId) {
        if (ObjectUtils.isEmpty(departmentId)) {
            throw new InvalidRequestException("Department id is empty");
        }
        return employeeRepository.findAllByDepartmentId(departmentId).stream().map(HmsEmployee::new).toList();
    }
}
