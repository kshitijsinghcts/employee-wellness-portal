package com.example.wellnessportal.model;

/**
 * Data Transfer Object for an Employee.
 * This class is used to transfer employee data to the client without exposing
 * sensitive information like passwords.
 * The role is hardcoded to "EMPLOYEE".
 */
public class EmployeeDTO {
    private Long employeeId;
    private String name;
    private String email;
    private final String role = "EMPLOYEE";

    public EmployeeDTO() {

    }

    /**
     * Constructs a new EmployeeDTO.
     * Used in services like `EmployeeService` to map from the `Employee` entity.
     *
     * @param employeeId The employee's ID.
     * @param name       The employee's name.
     * @param email      The employee's email.
     */
    public EmployeeDTO(Long employeeId, String name, String email) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getRole() {
        return role;
    }
}