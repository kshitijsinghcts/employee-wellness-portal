package com.example.wellnessportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.AuthUser;

import com.example.wellnessportal.repository.AdminRepository;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AuthUserRepository;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public String validateEmployee(AuthUser inputAuthUser) {

        long employeeId = inputAuthUser.getEmployeeId();
        AuthUser authUser = authUserRepository.findById(employeeId).orElse(null);
        System.out.println(authUser);

        if (authUser == null || !inputAuthUser.getPassword().equals(authUser.getPassword())) {
            return "Invalid credentials !";
        }

        String role = inputAuthUser.getRole();
        if ("ADMIN".equals(role)) {
            Admin admin = adminRepository.findAdminByEmployeeId(employeeId);
            if (admin != null) {
                // Return dummy token for admin
                return "dummy-token-for-admin-" + employeeId;
            }
        } else if ("EMPLOYEE".equals(role)) {
            Employee employee = employeeRepository.findEmployeeByEmployeeId(employeeId);
            if (employee != null) {
                // Return dummy token for employee
                return "dummy-token-for-employee-" + employeeId;
            }
        } else {
            return "Create your account";
        }
        return "Invalid credentials. Please try again";

    }

    public String registerEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmployeeId())
                ||
                authUserRepository.existsById(employee.getEmployeeId())) {
            return "Employee with ID " + employee.getEmployeeId() + " already exists. Kindly login.";
        }
        employeeRepository.save(employee);
        authUserRepository.save(new AuthUser(employee.getEmployeeId(),
                employee.getEmail(),
                "EMPLOYEE"));
        return "Employee registered successfully with ID " + employee.getEmployeeId();
    }

}
