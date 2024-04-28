package com.disertation.hmshr.web;

import com.disertation.hmshr.domain.HmsEmployee;
import com.disertation.hmshr.entities.Employee;
import com.disertation.hmshr.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public HmsEmployee createEmployee(@RequestBody HmsEmployee employee) {
        log.info("Creating employee : {}", employee);
        try {
            HmsEmployee createdEmployee = employeeService.createEmployee(employee);
            log.info("Created employee : {}", createdEmployee);
            return createdEmployee;
        } catch (Exception e) {
            log.error("Error creating employee : {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<HmsEmployee> getAllEmployees() {
        log.info("Getting all employees");
        try {
            List<HmsEmployee> employees = employeeService.getAllEmployees();
            log.info("Retrieved {} employees.", employees.size());
            return employees;
        } catch (Exception e) {
            log.error("Error getting all employees: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public HmsEmployee getEmployee(@PathVariable("id") Long id) {
        log.info("Getting employee : {}", id);
        try {
            HmsEmployee employee = employeeService.getEmployeeById(id);
            log.info("Retrieved employee : {}", employee);
            return employee;
        } catch (Exception e) {
            log.error("Error getting employee with id: {}, error : {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/department/{departmentId}")
    public List<HmsEmployee> getEmployeeByDepartment(@PathVariable("departmentId") Long departmentId) {
        log.info("Getting employee by department : {}", departmentId);
        try {
            List<HmsEmployee> employees = employeeService.getEmployeesByDepartmentId(departmentId);
            log.info("Retrieved {} employees for department id: {}.", employees.size(), departmentId);
            return employees;
        } catch (Exception e) {
            log.error("Error retrieving employee for department with id: {}, error : {}", departmentId, e.getMessage());
            throw e;
        }
    }
}
