package com.example.wellnessportal.service.adminservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.AuthUser;

import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AuthUserRepository;
@Service
class AccountCreationService {

    @Autowired 
    public AuthUserRepository authUserRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    String createAccount(Long employeeId, 
                         String password, 
                         String role, 
                         String name, 
                         String email) {
        AuthUser authUser = new AuthUser(employeeId,
                                         password,
                                         role);
        authUser.setEmployeeId(employeeId);
        authUser.setPassword(password);
        authUser.setRole("Employee");
        authUserRepository.save(authUser);

        if (role.equals("EMPLOYEE")) 
        {
            Employee employee = new Employee(employeeId,
                                             password, 
                                             name, 
                                             email,
                                             role,
                                             0,
                                             null);
         
            employeeRepository.save(employee);
        
        return "Account for employee ID " + employeeId + " has been created successfully";
    }
    else
    return "Account can only be created for Employee";
}
}
