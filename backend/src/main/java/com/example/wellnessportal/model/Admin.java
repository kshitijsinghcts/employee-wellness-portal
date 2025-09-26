package com.example.wellnessportal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Administrator in the system.
 * This is a JPA entity that maps to the 'admin' table in the database.
 */
@Entity
public class Admin {

    /** The employee ID, which serves as the primary key. */
    @Id
    private Long employeeId;

    /** The admin's password. It is write-only for security purposes. */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    @Column(unique = true)
    private String email;

    public Admin() {
        // Default constructor required by JPA.
    }

    /**
     * Constructs a new Admin with all required fields.
     * This constructor is typically used when creating a new admin instance, for
     * example during registration.
     *
     * @param employeeId The employee ID for the admin.
     * @param password   The admin's password.
     * @param name       The admin's name.
     * @param email      The admin's email address.
     */
    public Admin(Long employeeId,
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

    public String getName() {
        return this.name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}