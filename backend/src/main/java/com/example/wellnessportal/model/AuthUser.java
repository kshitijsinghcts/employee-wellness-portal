package com.example.wellnessportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AuthUser {

    @Id
    private Long employeeId;
    // Username can be customized by the employee but employee id is a mandatory
    // field
    private String email;
    private String password;
    // For Role-Based Login
    private String role;

    public AuthUser() {
    }

    // what we need for login
    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
        if (email != null && email.endsWith("@portaladmin.com")) {
            this.role = "ADMIN";
        } else {
            this.role = "EMPLOYEE";
        }
    }

    public AuthUser(Long employeeId,
            String email,
            String password,
            String role) {
        this.employeeId = employeeId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

   
    // Getters and setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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