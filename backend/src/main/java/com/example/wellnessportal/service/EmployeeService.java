

package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.AdminDTO;
import com.example.wellnessportal.model.EmployeeDTO;

import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Since admins are also employees, their operations are also performed here
@Service
public class EmployeeService 
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    // The following methods can be used by adminfor analytics:

// Get all employees as DTOs
public List<EmployeeDTO> getAllEmployees() 
{
    return employeeRepository.findAll()
            .stream()
            .map(emp -> new EmployeeDTO(emp.getEmployeeId(), emp.getName(), emp.getEmail()))
            .collect(Collectors.toList());
}

// Get employee by ID as Optional<EmployeeDTO>
public Optional<EmployeeDTO> getEmployeeById(Long id) 
{
    return employeeRepository.findById(id)
            .map(emp -> new EmployeeDTO(emp.getEmployeeId(), emp.getName(), emp.getEmail()));
}

// Get admin by employee ID as AdminDTO
public AdminDTO getAdminByEmployeeId(Long employeeId) 
{
    Admin admin = adminRepository.findAdminByEmployeeId(employeeId);
    if (admin == null) return null;
    return new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail());
}

// Get admin by email as AdminDTO
public AdminDTO getAdminByEmail(String email) 
{
    Admin admin = adminRepository.findAdminByEmail(email);
    if (admin == null) return null;
    return new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail());
}

// Update employee (returns updated EmployeeDTO)
public EmployeeDTO updateEmployee(Long id, Employee updatedEmployee) 
{
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
