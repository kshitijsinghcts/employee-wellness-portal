package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "wellnessmetric", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "employeeId", "recordDate" })
})
public class WellnessMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    private Long employeeId;

    private LocalDate recordDate;

    private String mood;

    private int sleepHours;

    private int dailySteps;

    private int waterIntake;

    @ElementCollection
    private List<Integer> scores;

    // Constructors
    public WellnessMetric() {

    }

    // For first-time record creation
    /*
     * Default values for the fields are:
     * Employee Id must be provided
     * recordDate is registered date
     * mood empty string
     * sleepHours is 0
     * dailySteps is 0
     * waterIntake is 0
     */
    public WellnessMetric(Long employeeId) {
        this.employeeId = employeeId;
        this.recordDate = LocalDate.now();
        this.mood = "";
        this.sleepHours = 0;
        this.dailySteps = 0;
        this.waterIntake = 0;
    }

    // UI-specific
    public WellnessMetric(Long employeeId,
            String mood,
            int sleepHours,
            int dailySteps,
            int waterIntake) {
        this.employeeId = employeeId;
        this.recordDate = LocalDate.now();
        this.mood = mood;
        this.sleepHours = sleepHours;
        this.dailySteps = dailySteps;
        this.waterIntake = waterIntake;

    }

    public WellnessMetric(Long employeeId,
            LocalDate recordDate,
            String mood,
            int sleepHours,
            int dailySteps,
            int waterIntake) {
        this.employeeId = employeeId;
        this.recordDate = recordDate;
        this.mood = mood;
        this.sleepHours = sleepHours;
        this.dailySteps = dailySteps;
        this.waterIntake = waterIntake;

    }

    // Getters and Setters
    public Long getMetricId() {
        return metricId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getDailySteps() {
        return dailySteps;
    }

    public void setDailySteps(int dailySteps) {
        this.dailySteps = dailySteps;
    }

    public int getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(int waterIntake) {
        this.waterIntake = waterIntake;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
}
