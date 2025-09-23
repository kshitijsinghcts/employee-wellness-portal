package com.example.wellnessportal.model;

// DTO for Employee (role hardcoded as "EMPLOYEE")
public class EmployeeDTO {
    private Long employeeId;
    private String name;
    private String email;
    private final String role = "EMPLOYEE";

    public EmployeeDTO() 
    {

    }

    public EmployeeDTO(Long employeeId, String name, String email) 
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

    public void setName(String name) 
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
}