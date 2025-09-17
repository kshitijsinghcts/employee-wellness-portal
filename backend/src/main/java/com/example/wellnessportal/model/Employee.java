package com.example.wellnessportal.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Employee {
    
    @Id
    private Long employeeId;
    private String password;
    private String name;
    private String email;
   

   

    public Employee()
     {
    }

    public Employee(Long employeeId,
            String password,
            String name,
            String email)
            {
        this.employeeId = employeeId;
        this.password = password;
        this.name = name;
        this.email = email;
      
    }

    // Constructor for Login. Fetch other details from repository.
    public Employee(Long employeeId,
        String password)
    {
        this.employeeId = employeeId;
        this.password = password;
    }


    // Getters and setters
    public Long getEmployeeId() {
        return employeeId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

   
    }
