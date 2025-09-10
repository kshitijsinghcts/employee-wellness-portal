package com.example.wellnessportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    private Long employeeId;
    private String goalType;
    private LocalDate targetDate;
    private int status;
    //Attributes For Performance Measurement API
    private int targetScores;
    private Rewards targetRewards;

    public Goal(Long employeeId, 
                String goalType,
                LocalDate targetDate, 
                int status, 
                int targetScores, 
                Rewards targetRewards) 
                {
        this.employeeId = employeeId;
        this.goalType = goalType;
        this.targetDate = targetDate;
        this.status = status;
        this.targetScores = targetScores;
        this.targetRewards = targetRewards;
    }
    // Getters and setters
    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public String getGoalType() 
    {
        return goalType;
    }

    public void setGoalType(String goalType) 
    {
        this.goalType = goalType;
    }


    public LocalDate getTargetDate() 
    {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) 
    {
        this.targetDate = targetDate;
    }

    public int getStatus() 
    {
        return status;
    }

    public void setStatus(int status) 
    {
        this.status = status;
    }

    public int getTargetScores() 
    {
        return targetScores;
    }

    public void setTargetScores(int targetScores) 
    {
        this.targetScores = targetScores;
    }

    public Rewards getTargetRewards() 
    {
        return targetRewards;
    }

    public void setTargetRewards(Rewards targetRewards) {
        this.targetRewards = targetRewards;
    }
}