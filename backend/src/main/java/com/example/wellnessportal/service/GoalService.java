package com.example.wellnessportal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.GoalRepository;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public String setGoal(Goal goal) {
        if (goal == null || goal.getEmployeeId() == null) {
            throw new IllegalArgumentException("Goal or Employee ID cannot be empty");
        }
        goalRepository.save(goal);
        return "Goal set successfully";
    }

    public void deleteGoal(Goal goal) {
        if (goal != null)
            goalRepository.delete(goal);

    }

    public List<Goal> findGoalsByEmployeeId(Long employeeId) {
        return goalRepository.findGoalsByEmployeeId(employeeId);
    }

    /*
     * The following methods are used as utilities by Rewards class:
     * 1. Finding status of specific goal specified by the user
     * 2. Finding status of all goals of the particular user
     * 3. Updation of goal statuses from time to time based on user logs
     */

    public void setStatus(Long employeeId, int status) {
        List<Goal> goals = findGoalsByEmployeeId(employeeId);
        for (Goal goal : goals) {
            goal.setStatus(status);
            goalRepository.save(goal);
        }
    }

    // This method check whether the individual is on track in all kinds of goals
    // set by him/her
    public boolean validateGoalCompletion(long employeeId,
            WellnessMetric wellnessMetric,
            LocalDate recordDate) {

        List<Goal> goals = findGoalsByEmployeeId(employeeId);
        List<Boolean> goalStatus=new ArrayList<>();

        for (Goal goal : goals) {
            System.out.println(goal.getGoalType()+","+recordDate);
            if (!isGoalCompleted(goal,
                    wellnessMetric,
                    recordDate)) {
                return false;
            }
        }

        return true;
    }

    // This overloaded version checks individual goal status
    public boolean validateGoalCompletion(long employeeId,
            WellnessMetric wellnessMetric,
            Goal goal,
            LocalDate recordDate) {

        if (!isGoalCompleted(goal,
                wellnessMetric,
                recordDate)) {
            return false;
        }

        return true;
    }

    // Verify completion of each metric before the deadline
    private boolean isGoalCompleted(Goal goal,
            WellnessMetric wellnessMetric,
            LocalDate recordDate) {
        switch (goal.getGoalType()) {
            case "mood" -> {
                return wellnessMetric.getMood().equalsIgnoreCase("Happy")
                        &&
                        goal.getTargetDate().isBefore(recordDate);
            }

            case "dailySteps" -> {

                goal.setStatus(wellnessMetric.getDailySteps());
            }

            case "sleepHours" -> {
                goal.setStatus(wellnessMetric.getSleepHours());
            }

            case "waterIntake" -> {
                goal.setStatus(wellnessMetric.getWaterIntake());
            }

        }
        return ((goal.getStatus() >= goal.getTargetScores())
                &&
                goal.getTargetDate().isBefore(recordDate))
                ||
                ((goal.getStatus() <= goal.getTargetScores())
                &&
                goal.getTargetDate().isAfter(recordDate));
    }
}
