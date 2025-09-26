package com.example.wellnessportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user's authentication credentials in the system.
 * This is a JPA entity that maps to the 'auth_user' table and is used for login
 * and role management.
 */
@Entity
public class AuthUser {

    @Id
    private Long employeeId;

    /** The user's email, which serves as their username and must be unique. */
    @Column(unique = true)
    private String email;

    /** The user's password. It is write-only for security purposes. */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** The user's role (e.g., "ADMIN", "EMPLOYEE") for authorization. */
    private String role;

    public AuthUser() {
        // Default constructor required by JPA.
    }

    /**
     * Constructs an AuthUser instance from login credentials.
     * This is used by Jackson to deserialize the request body from the
     * `/api/auth/login` endpoint.
     * The role is automatically inferred based on the email domain.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructs a new AuthUser with all fields.
     * This is used in the `AuthService` when registering a new employee or admin.
     *
     * @param employeeId The unique ID for the user.
     * @param email      The user's email address.
     * @param password   The user's password.
     * @param role       The user's role.
     */
    public AuthUser(Long employeeId,
            String email,
            String password,
            String role) {
        this.employeeId = employeeId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // --- Getters and Setters ---
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