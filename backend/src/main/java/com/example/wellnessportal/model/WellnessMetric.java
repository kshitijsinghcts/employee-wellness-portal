package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class WellnessMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    private Long employeeId;

    private LocalDate currentDate;

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
    private List<Integer> scores;

    

    // Constructors
    public WellnessMetric(Long employeeId,
                          LocalDate currentDate,
                          String mood,
                          int sleepHours,
                          int dailySteps,
                          int waterIntake,
                          List<Rewards> rewards) 
       {
        this.employeeId = employeeId;
        this.currentDate = currentDate;
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

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
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

    public int getWaterIntake() 
    {
        return waterIntake;
    }
    public void setWaterIntake(int waterIntake) 
    {
        this.waterIntake = waterIntake;
    }
    public List<Rewards> getRewards() {
        return rewards;
    }

    public void setRewards(List<Rewards> rewards) {
        this.rewards = rewards;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
}
