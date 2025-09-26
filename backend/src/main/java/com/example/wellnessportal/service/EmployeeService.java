
package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AdminDTO;
import com.example.wellnessportal.model.EmployeeDTO;

import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing employee and administrator data.
 * Provides methods for retrieving and updating user information, converting
 * entities to DTOs to prevent exposing sensitive data.
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Retrieves all employees and converts them to {@link EmployeeDTO} objects.
     * 
     * @return A list of all employees as DTOs.
     */
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getEmployeeId(), emp.getName(), emp.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single employee by their ID and converts it to an
     * {@link EmployeeDTO}.
     * 
     * @param id The ID of the employee to retrieve.
     * @return An {@link Optional} containing the EmployeeDTO if found, otherwise
     *         empty.
     */
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(emp -> new EmployeeDTO(emp.getEmployeeId(), emp.getName(), emp.getEmail()));
    }

    /**
     * Retrieves an admin by their employee ID and converts it to an
     * {@link AdminDTO}.
     * 
     * @param employeeId The employee ID of the admin.
     * @return An AdminDTO if found, otherwise null.
     */
    public AdminDTO getAdminByEmployeeId(Long employeeId) {
        Admin admin = adminRepository.findAdminByEmployeeId(employeeId);
        if (admin == null)
            return null;
        return new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail());
    }

    /**
     * Retrieves an admin by their email and converts it to an {@link AdminDTO}.
     * 
     * @param email The email of the admin.
     * @return An AdminDTO if found, otherwise null.
     */
    public AdminDTO getAdminByEmail(String email) {
        Admin admin = adminRepository.findAdminByEmail(email);
        if (admin == null)
            return null;
        return new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail());
    }

    /**
     * Updates an employee's information.
     * 
     * @param id              The ID of the employee to update.
     * @param updatedEmployee An {@link Employee} object with the new details.
     * @return The updated employee as an {@link EmployeeDTO}.
     * @throws RuntimeException if the employee with the given ID is not found.
     */
    public EmployeeDTO updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setEmail(updatedEmployee.getEmail());
                    Employee saved = employeeRepository.save(employee);
                    return new EmployeeDTO(saved.getEmployeeId(), saved.getName(), saved.getEmail());
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
