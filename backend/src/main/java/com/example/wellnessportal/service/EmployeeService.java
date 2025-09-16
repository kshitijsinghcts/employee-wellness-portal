package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Since admins are also employees, their operations are also performed here
@Service
public class EmployeeService 
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    // The following methods can be used by adminfor analytics:

    public List<Employee> getAllEmployees() 
    {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) 
    {
        return employeeRepository.findById(id);
    }

    /* The following methods can be invoked by employee to search for admins:
       1. Employee Id
       2. Email
    */
    
    public Admin getAdminByEmployeeId(Long employeeId)
    {
        return adminRepository.findAdminByEmployeeId(employeeId);
    }

    public Admin getAdminByEmail(String email)
    {
       return adminRepository.findAdminByEmail(email);
    }

    // Handle exception in controller
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setEmail(updatedEmployee.getEmail());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
