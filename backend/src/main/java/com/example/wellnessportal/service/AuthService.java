package com.example.wellnessportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.AuthUser;

import com.example.wellnessportal.repository.AdminRepository;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AuthUserRepository;

import com.example.wellnessportal.util.JwtUtil;

@Service
public class AuthService {

      @Autowired
      private AdminRepository adminRepository;

      @Autowired
      private EmployeeRepository employeeRepository;

      @Autowired
      private AuthUserRepository authUserRepository;

      public String validateEmployee(AuthUser inputAuthUser) 
      {

    long employeeId = inputAuthUser.getEmployeeId();
    AuthUser authUser = authUserRepository.findById(employeeId).orElse(null);

    if (!inputAuthUser.getPassword().equals(authUser.getPassword())|| authUser == null) 
    {
        return "Invalid credentials";
    }


    String role = inputAuthUser.getRole();
    if ("ADMIN".equals(role))
     {
        Admin admin = adminRepository.findAdminByEmployeeId(employeeId);
        if (admin != null) 
        {
            // Generate JWT token for admin
            return JwtUtil.generateToken(String.valueOf(authUser.getEmployeeId()), 
                                         role);
        }
    } 
    else if ("EMPLOYEE".equals(role)) 
    {
        Employee employee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        if (employee != null) 
        {
            // Generate JWT token for employee
            return JwtUtil.generateToken(String.valueOf(authUser.getEmployeeId()),
                                         role);
        }
    } else 
    {
        return "Create your account";
    }
    return "Invalid credentials. Please try again";
    
}
    
public String registerEmployee(Employee employee) 
{
    if (employeeRepository.existsById(employee.getEmployeeId())
        ||
        authUserRepository.existsById(employee.getEmployeeId())) 
    {
        return "Employee with ID " + employee.getEmployeeId() + " already exists. Kindly login.";
    }
    employeeRepository.save(employee);
    authUserRepository.save(new AuthUser(employee.getEmployeeId(), 
                                         employee.getEmail(), 
                                         "EMPLOYEE"));
    return "Employee registered successfully with ID " + employee.getEmployeeId();
}


    

}
