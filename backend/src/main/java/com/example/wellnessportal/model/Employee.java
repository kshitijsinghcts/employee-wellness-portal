package com.example.wellnessportal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Employee in the system.
 * This is a JPA entity that maps to the 'employee' table in the database.
 */
@Entity
public class Employee {

    /** The employee's unique ID, which serves as the primary key. */
    @Id
    private Long employeeId;
    /** The employee's password. It is write-only for security purposes. */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    /** The employee's email address, which must be unique. */
    @Column(unique = true)
    private String email;

    public Employee() {
        // Default constructor required by JPA.
    }

    /**
     * Constructs a new Employee with all required fields.
     * This constructor is used when creating a new employee instance, for example
     * during registration.
     *
     * @param employeeId The unique ID for the employee.
     * @param password   The employee's password.
     * @param name       The employee's name.
     * @param email      The employee's email address.
     */
    public Employee(Long employeeId,
            String password,
            String name,
            String email) {
        this.employeeId = employeeId;
        this.password = password;
        this.name = name;
        this.email = email;

    }

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
