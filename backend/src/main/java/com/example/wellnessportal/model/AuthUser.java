package com.example.wellnessportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class AuthUser {

    @Id
    private Long EmployeeId;
    // Username can be customized by the employee but employee id is a mandatory field
    
    private String password;
    //For Role-Based Login
    private String role;
    
    public AuthUser(Long employeeId, 
                     
                    String password, 
                    String role) {
        this.EmployeeId = employeeId;
        this.password = password;
        this.role = role;
    }
    // Getters and setters
    public Long getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.EmployeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}