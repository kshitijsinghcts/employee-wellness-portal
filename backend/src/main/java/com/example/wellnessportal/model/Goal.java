package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a wellness goal set by an employee.
 * This is a JPA entity that maps to the 'goal' table in the database.
 */
@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    private Long employeeId;
    private String title;
    private LocalDate targetDate;
    private int status;
    // Attributes For Performance Measurement API
    private int targetScores;
    private int points;
    private String targetValue;

    private String description;

    public Goal() {
        // Default constructor required by JPA.
    }

    /**
     * Constructor for creating a default Goal record, for example, upon user
     * registration.
     * Initializes with default values.
     *
     * @param employeeId The ID of the employee.
     */
    public Goal(Long employeeId) {
        this.employeeId = employeeId;
        this.title = "";
        this.description = "";
        this.targetDate = LocalDate.now();
        this.targetScores = 0;
        this.points = 0;
        this.targetValue = "";

    }

    /**
     * Constructs a new Goal with all performance-related attributes.
     * Used by the `GoalsController` when creating or updating a goal.
     *
     * @param employeeId   The ID of the employee.
     * @param title        The title of the goal.
     * @param description  A description of the goal.
     * @param targetDate   The date by which the goal should be achieved.
     * @param targetScores The target score or value for the goal.
     * @param targetValue  A string representation of the target value (e.g.,
     *                     "Bronze").
     */
    public Goal(Long employeeId,
            String title,
            String description,
            LocalDate targetDate,
            int targetScores,
            String targetValue) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.targetDate = targetDate;
        this.targetScores = targetScores;
        this.points = 0; // Or some other default
        this.targetValue = targetValue;
    }

    /**
     * Constructs a new Goal with a subset of attributes.
     * Used by the `DataInitializer` to seed the database with sample goals.
     *
     * @param employeeId  The ID of the employee.
     * @param title       The title of the goal.
     * @param description A description of the goal.
     * @param targetDate  The date by which the goal should be achieved.
     * @param targetValue A string representation of the target value.
     */
    public Goal(Long employeeId, String title, String description, LocalDate targetDate, String targetValue) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.targetDate = targetDate;
        this.status = -1; // Default to active
        this.targetValue = targetValue;
    }

    // --- Getters and Setters ---
    public Long getGoalId() {
        return this.goalId;
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
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTargetScores() {
        return targetScores;
    }

    public void setTargetScores(int targetScores) {
        this.targetScores = targetScores;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    @Override
    public String toString() {
        return "[goalId=" + goalId + ", employeeId=" + employeeId + ", title=" + title + ", targetDate="
                + targetDate + ", status=" + status + ", targetScores=" + targetScores + ", points=" + points
                + ", targetValue=" + targetValue + ", description=" + description + "]";
    }
}