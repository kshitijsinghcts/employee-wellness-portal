package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

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

    // Constructors of Goal:
    public Goal() {

    }

    // For first-time record creation
    /*
     * Default values for the fields are:
     * Employee Id must be provided
     * title is an empty string
     * description empty string
     * targetDate is registered date
     * targetScores is 0
     * targetValue is Bronze
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

    // we will use this in controller
    public Goal(Long employeeId,
            String title,
            String description,
            String targetDate) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.targetDate = LocalDate.parse(targetDate);
        this.targetScores = 0;
        this.points = 0;
        this.targetValue = "";
    }

    // Useful in Rewards Service where description is not necessary
    public Goal(Long employeeId,
            String title,
            LocalDate targetDate,
            int targetScores,
            String targetValue) {
        this.employeeId = employeeId;
        this.title = title;
        this.targetDate = targetDate;
        this.targetScores = targetScores;
        this.points = 0; // Or some other default
        this.targetValue = targetValue;
    }

    // what is used in controller
    public Goal(Long employeeId,
            String goalType,
            String description,
            LocalDate targetDate,
            String targetValue) {
        this.employeeId = employeeId;
        this.title = goalType; // also badly named this is title
        this.description = description;
        this.targetDate = targetDate;
        this.status = -1; // -1 means going, 0 means submitted, 1 means completed
        this.targetScores = 0;
        this.points = 0;
        this.targetValue = targetValue;
    }

    // Getters and setters
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