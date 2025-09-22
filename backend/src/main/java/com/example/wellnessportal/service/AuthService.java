package com.example.wellnessportal.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.WellnessMetric;

import com.example.wellnessportal.repository.AdminRepository;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.GoalRepository;
import com.example.wellnessportal.repository.WellnessMetricRepository;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    public String validateEmployee(AuthUser inputAuthUser) {

        String email = inputAuthUser.getEmail();

        AuthUser authUser = authUserRepository.findUserByEmail(email);

        if (authUser == null) {
            return "Invalid credentials. User not found.";
        }
        if (!inputAuthUser.getPassword().equals(authUser.getPassword())) {
            return "Invalid credentials. Incorrect password.";
        }
        Long employeeId = authUser.getEmployeeId();

        String role = authUser.getRole();
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
        if (employee.getEmployeeId() == null) {
            return "Employee ID is required.";
        }
        if (employeeRepository.existsById(employee.getEmployeeId())
                ||
                authUserRepository.existsById(employee.getEmployeeId())) {
            return "Employee with ID " + employee.getEmployeeId() + " already exists. Kindly login.";
        }

        /*
         * Storing user in 2 databases:
         * 1. AuthUser: Contains both employees and admins
         * 2. Employee: Contains only employees
         * 
         */
        employeeRepository.save(employee);
        authUserRepository.save(new AuthUser(
                employee.getEmployeeId(),
                employee.getEmail(),
                employee.getPassword(),
                "EMPLOYEE"));

        // Creating a record in each table which has employeeId immediately upon
        // registering user
        /*
         * The tables associated with employee Id as primary/composite key are:
         * 1. Goal
         * 2. WellnessMetric
         * Hence, their objects are created as:
         * 1. goalRecord
         * 2. wellnessMetricRecord
         */
        // Goal goalRecord= new Goal(employee.getEmployeeId());
        // WellnessMetric wellnessMetricRecord=new
        // WellnessMetric(employee.getEmployeeId());

        // goalRepository.save(goalRecord);
        // wellnessMetricRepository.save(wellnessMetricRecord);

        return "Employee registered successfully with ID " + employee.getEmployeeId();
    }

    public String registerAdmin(Admin admin) {
        if (admin.getEmployeeId() == null) {
            return "Admin Employee ID is required.";
        }
        if (employeeRepository.existsById(admin.getEmployeeId())
                ||
                authUserRepository.existsById(admin.getEmployeeId())) {
            return "Admin with ID " + admin.getEmployeeId() + " already exists. Kindly login.";
        }

        /*
         * Storing user in 2 databases:
         * 1. AuthUser: Contains both employees and admins
         * 2. Admin: Contains only admins
         * 
         */
        adminRepository.save(admin);
        authUserRepository.save(new AuthUser(
                admin.getEmployeeId(),
                admin.getEmail(),
                admin.getPassword(),
                "ADMIN"));

        // Creating a record in each table which has employeeId immediately upon
        // registering user
        /*
         * The tables associated with employee Id as primary/composite key are:
         * 1. Goal
         * 2. WellnessMetric
         * Hence, their objects are created as:
         * 1. goalRecord
         * 2. wellnessMetricRecord
         */
        Goal goalRecord = new Goal(admin.getEmployeeId());
        WellnessMetric wellnessMetricRecord = new WellnessMetric(admin.getEmployeeId());

        goalRepository.save(goalRecord);
        wellnessMetricRepository.save(wellnessMetricRecord);

        return "Admin registered successfully with ID " + admin.getEmployeeId();
    }

    // This maps email to employeeId
    // Exception can be caught in controller stating that email doesnt exist
    public Long getAdminIdByEmail(String email) throws Exception {
        Admin admin = adminRepository.findAdminByEmail(email);
        try {
            if (admin != null)
                return admin.getEmployeeId();

            else
                throw new Exception();
        } catch (Exception e) {
            return (long) 0.0;
        }

    }

    public Long getEmployeeIdByEmail(String email) throws Exception {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        try {
            if (employee != null)
                return employee.getEmployeeId();
            else
                throw new Exception();
        } catch (Exception e) {
            return (long) 0.0;
        }

    }

}
