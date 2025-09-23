package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="wellnessmetric")
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

    @ElementCollection(targetClass = Rewards.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "wellness_rewards", joinColumns = @JoinColumn(name = "metric_id"))
    @Column(name = "reward")
    private List<Rewards> rewards;

    @ElementCollection
    private double scores;

    // Attributes dealt by admin
    private static double dailyStepsWeight = 1.0;
    private static double sleepHoursWeight = 1.0;
    private static double waterIntakeWeight = 1.0;

   

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
            int waterIntake,
            List<Rewards> rewards) {
        this.employeeId = employeeId;
        this.recordDate = recordDate;
        this.mood = mood;
        this.sleepHours = sleepHours;
        this.dailySteps = dailySteps;
        this.waterIntake = waterIntake;
        this.rewards = rewards;

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

    public LocalDate getrecordDate() {
        return recordDate;
    }

    public void setrecordDate(LocalDate recordDate) {
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

    public List<Rewards> getRewards() {
        return rewards;
    }

    public void setRewards(List<Rewards> rewards) {
        this.rewards = rewards;
    }

    public double getScores() {
        return scores;
    }

    public void setScores(double score) {
        this.scores = score;
    }

    //These all are defined by admin

     // Setter methods to configure weights
    public static void setDailyStepsWeight(double weight) {
        dailyStepsWeight = weight;
    }

    public static void setSleepHoursWeight(double weight) {
        sleepHoursWeight = weight;
    }

    public static void setWaterIntakeWeight(double weight) {
        waterIntakeWeight = weight;
    }

    // Optional: Getter methods if needed
    public static double getDailyStepsWeight() {
        return dailyStepsWeight;
    }

    public static double getSleepHoursWeight() {
        return sleepHoursWeight;
    }

    public static double getWaterIntakeWeight() {
        return waterIntakeWeight;
    }
}
