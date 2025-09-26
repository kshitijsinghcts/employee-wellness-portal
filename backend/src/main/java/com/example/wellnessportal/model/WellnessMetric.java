package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a daily wellness metric log for an employee.
 * This is a JPA entity that maps to the 'wellnessmetric' table.
 * A unique constraint ensures that an employee can only have one entry per day.
 */
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

    public WellnessMetric() {
        // Default constructor required by JPA.
    }

    /**
     * Constructor for creating a default WellnessMetric record, for example, upon
     * user registration.
     * Initializes metrics to zero or empty.
     *
     * @param employeeId The ID of the employee.
     */
    public WellnessMetric(Long employeeId) {
        this.employeeId = employeeId;
        this.recordDate = LocalDate.now();
        this.mood = "";
        this.sleepHours = 0;
        this.dailySteps = 0;
        this.waterIntake = 0;
    }

    /**
     * Constructs a new WellnessMetric with all fields.
     * This is the primary constructor used when creating a new metric log.
     *
     * @param employeeId  The ID of the employee.
     * @param recordDate  The date the metric was recorded.
     * @param mood        The employee's reported mood.
     * @param sleepHours  The number of hours slept.
     * @param dailySteps  The number of steps taken.
     * @param waterIntake The amount of water consumed.
     */
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

    // --- Getters and Setters ---
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

}
