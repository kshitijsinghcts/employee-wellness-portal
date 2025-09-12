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
    private String role;
    private int scores;

    @ElementCollection(targetClass = Rewards.class)
    private List<Rewards> rewards;

    public Employee() {
    }

    public Employee(Long employeeId,
            String password,
            String name,
            String email) {
        this.employeeId = employeeId;
        this.password = password;
        this.name = name;
        this.email = email;
        if (email != null && email.endsWith("@portaladmin.com")) {
            this.role = "ADMIN";
        } else {
            this.role = "EMPLOYEE";
        }
        this.scores = 0;
        this.rewards = List.of();
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public List<Rewards> getRewards() {
        return rewards;
    }

    public void setRewards(List<Rewards> rewards) {
        this.rewards = rewards;
    }
}