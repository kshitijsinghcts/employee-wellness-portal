package com.example.wellnessportal.model;

import java.util.ArrayList;
import java.util.List;


// DTO for Admin (role hardcoded as "ADMIN")
public class AdminDTO {
    private Long employeeId;
    private String name;
    private String email;
    private final String role = "ADMIN";
    private List<Employee> employeeList;

    public AdminDTO() 
    {

    }

    public AdminDTO(Long employeeId, String name, String email) 
    {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public String getName() 
    {
        return name;
    }

    public void setName()
    {
           this.name = name;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getRole() 
    {
        return role;
    }

    public List<Employee> getListOfEmployees()
    {
        return this.employeeList;
    }

    public void setListOfEmployees()
    {
        this.employeeList=employeeList;
    }

    
   // Add an employee
    public void addEmployee(Employee employee) 
    {
        if (employeeList == null) {
            employeeList = new ArrayList<>();
        }
        employeeList.add(employee);
    }

    // Remove an employee by ID
    public boolean removeEmployee(Long employeeId) 
    {
        if (employeeList == null) return false;
        return employeeList.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
    }

    // Fetch an employee by ID
    public Employee fetchEmployee(Long employeeId) 
    {
        if (employeeList == null) return null;
        return employeeList.stream()
                .filter(emp -> emp.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

}