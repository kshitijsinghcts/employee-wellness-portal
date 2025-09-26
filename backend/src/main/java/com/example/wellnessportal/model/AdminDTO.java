package com.example.wellnessportal.model;

/**
 * Data Transfer Object for an Admin.
 * This class is used to transfer admin data to the client without exposing
 * sensitive information like passwords.
 * The role is hardcoded to "ADMIN".
 */
public class AdminDTO {
    private Long employeeId;
    private String name;
    private String email;
    private final String role = "ADMIN";

    public AdminDTO() {

    }

    public AdminDTO(Long employeeId, String name, String email) {
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