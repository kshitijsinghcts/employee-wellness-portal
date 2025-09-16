package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    private Long employeeId;
    private String goalType;
    private LocalDate targetDate;
    private int status;
    //Attributes For Performance Measurement API
    private int targetScores;
    private Rewards targetRewards;
  
    private String description;

    // Constructors of Goal:
    public Goal()
    {

    }

    //For first-time record creation
    /*
     * Default values for the fields are:
     * Employee Id must be provided
     * goalType is an empty string
     * description empty string
     * targetDate is registered date
     * targetScores is 0
     * targetRewards is Bronze
     */
    public Goal(Long employeeId)
    {
        this.employeeId=employeeId;
        this.goalType = "";
        this.description="";
        this.targetDate=LocalDate.now();
        this.targetScores = 0;
        this.targetRewards = Rewards.BRONZE;

    }

     public Goal(Long employeeId, 
        String goalType,
        String description,
        LocalDate targetDate, 
        int targetScores, 
        Rewards targetRewards) 
    {
        this.employeeId = employeeId;
        this.goalType = goalType;
        this.description=description;
        this.targetDate = targetDate;
        this.targetScores = targetScores;
        this.targetRewards = targetRewards;
    }

    //Useful in Rewards Service where description is not necessary
    public Goal(Long employeeId, 
        String goalType,
        LocalDate targetDate, 
        int targetScores, 
        Rewards targetRewards) 
    {
        this.employeeId = employeeId;
        this.goalType = goalType;
        this.targetDate = targetDate;
        this.targetScores = targetScores;
        this.targetRewards = targetRewards;
    }

    // Getters and setters
    public Long getGoalId()
    {
        return this.goalId;
    }

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

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description=description;
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