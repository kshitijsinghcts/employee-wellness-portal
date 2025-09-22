package com.example.wellnessportal.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Admin {

    // Employee ID as Primary Key for the admin
    @Id
    private Long employeeId;
    // List of Employees falling under the current HR
  
    // Attributes for Authentication
     private String password;
    private String name;
        @Column(unique=true)
    private String email;
   

    public Admin()
    {

    }
    public Admin(Long employeeId,
                 String password,
                 String name,
                 String email)
                 {
                    this.employeeId=employeeId;
                    this.name=name;
                    this.email=email;
                    this.password=password;
                 }

    // Constructor for Login. Fetch other details from repository.
    public Admin(String email,
        String password)
    {
        this.email = email;
        this.password = password;
    }

    public Admin(Long employeeId,
        String password)
    {
        this.employeeId = employeeId;
        this.password = password;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() 
    {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}