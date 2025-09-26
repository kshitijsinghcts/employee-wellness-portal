package com.example.wellnessportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * Represents a reward or achievement earned by an employee.
 * This is a JPA entity that maps to the 'rewards' table in the database.
 */
@Entity
public class Rewards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private String title;

    private String description;

    private LocalDate achievedDate;

    public Rewards() {
        // Default constructor required by JPA.
    }

    /**
     * Constructs a new Reward.
     * This is used by the `RewardsService` when a new achievement is granted to an
     * employee.
     *
     * @param employeeId   The ID of the employee who earned the reward.
     * @param title        The title of the reward (e.g., "10-Day Streak").
     * @param description  A description of what was achieved.
     * @param achievedDate The date the reward was earned.
     */
    public Rewards(Long employeeId, String title, String description, LocalDate achievedDate) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.achievedDate = achievedDate;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAchievedDate() {
        return achievedDate;
    }

    public void setAchievedDate(LocalDate achievedDate) {
        this.achievedDate = achievedDate;
    }
}