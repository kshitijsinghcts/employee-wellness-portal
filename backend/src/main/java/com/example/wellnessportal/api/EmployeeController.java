package com.example.wellnessportal.api;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.EmployeeDTO;
import com.example.wellnessportal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for employee-related operations.
 * Provides endpoints for retrieving and updating employee information.
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Retrieves a list of all employees.
     * 
     * @return A ResponseEntity containing a list of EmployeeDTOs and HTTP status
     *         200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Retrieves a single employee by their ID.
     * 
     * @param id The ID of the employee to retrieve.
     * @return A ResponseEntity containing the EmployeeDTO if found (status 200 OK),
     *         or a 404 Not Found response if the ID does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an employee's information.
     * 
     * @param id              The ID of the employee to update.
     * @param updatedEmployee A JSON object in the request body with the employee's
     *                        updated information (e.g., name, email).
     * @return A ResponseEntity containing the updated EmployeeDTO (status 200 OK),
     *         or a 404 Not Found response if the employee does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeInfo(@PathVariable Long id,
            @RequestBody Employee updatedEmployee) {
        try {
            EmployeeDTO employee = employeeService.updateEmployee(id, updatedEmployee);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}