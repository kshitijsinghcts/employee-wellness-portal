package com.example.wellnessportal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.WellnessMetricRepository;

@Service
public class WellnessMetricService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    public WellnessMetric saveWellnessMetric(Long employeeId,
                                             LocalDate date,
                                             String mood,
                                             int sleepHours,
                                             int activityLevel,
                                             int waterIntake) {

        AuthUser authUser = authUserRepository.findById(employeeId).orElse(null);
        
        if (authUser == null) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        // Determine rewards based on input values
        List<Rewards> rewards = new ArrayList<>();

        // Mood-based reward
        if (mood.equalsIgnoreCase("Happy")) {
            rewards.add(Rewards.PLATINUM);
        } 
        else if (mood.equalsIgnoreCase("Neutral")) {
            rewards.add(Rewards.GOLD);
        } 
        else {
            rewards.add(Rewards.SILVER);
        }

        // Sleep-based reward
        if (sleepHours >= 8) 
        {
            rewards.add(Rewards.PLATINUM);
        } 
        else if (sleepHours >= 6) 
        {
            rewards.add(Rewards.GOLD);
        }
        else if(sleepHours >= 5)
        {
            rewards.add(Rewards.SILVER);
        }
         else if(sleepHours >= 4)
         {
            rewards.add(Rewards.BRONZE);
         }
        

        // Activity-based reward
        if (activityLevel >= 9000) 
        {
            rewards.add(Rewards.PLATINUM);
        } 
        else if (activityLevel >= 7000) 
        {
            rewards.add(Rewards.GOLD);
        } 
        else if (activityLevel >= 5000) 
        {
            rewards.add(Rewards.SILVER);
        }
        else if(activityLevel >= 3000)
        {
            rewards.add(Rewards.BRONZE);
        }

        // Water intake-based reward
        if(waterIntake >= 8){
            rewards.add(Rewards.PLATINUM);
        } 
        else if(waterIntake >= 6)
        {
            rewards.add(Rewards.GOLD);
        } 
        else if(waterIntake >= 4)
        {
            rewards.add(Rewards.SILVER);
        } 
        else if(waterIntake >= 2){
            rewards.add(Rewards.BRONZE);
        }

        WellnessMetric wellnessMetric = new WellnessMetric(
            employeeId,
            date,
            mood,
            sleepHours,
            activityLevel,
            waterIntake,
            rewards
        );

        return wellnessMetricRepository.save(wellnessMetric);
    }

   

    public List<WellnessMetric> getEmployeeLogs(Long employeeId) 
    {
        return wellnessMetricRepository.findByEmployeeId(employeeId);
    }

    public String getOverallWellnessMetricsStatus(Long employeeId,
                                                  WellnessMetric wellnessMetric)
    {
         //Getting overall wellness metric status for all metrics of the user
        if(goalService.validateGoalCompletion(employeeId,
                                              wellnessMetric,
                                              LocalDate.now()))
        return "On Track";
        else
        return "Behind Schedule";
    }

    //Getting overall wellness metric status individually
    public String getOverallWellnessMetricsStatus(Long employeeId,
                                                  WellnessMetric wellnessMetric,  
                                                  Goal goal)
    {
        if(goalService.validateGoalCompletion(employeeId,
                                              wellnessMetric,
                                              goal,
                                              LocalDate.now()))
        return "On Track";
        else
        return "Behind Schedule";
    }

}
