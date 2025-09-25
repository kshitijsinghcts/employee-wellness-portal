package com.example.wellnessportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.GoalRepository;

/**
 * Service for managing employee wellness goals.
 * Handles the creation, deletion, and retrieval of goals.
 * This service is used by the `GoalsController`.
 */
@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    /**
     * Creates or updates a wellness goal for an employee.
     *
     * @param goal The {@link Goal} object to be saved.
     * @return A success message string.
     * @throws IllegalArgumentException if the goal or employee ID is null, or if
     *                                  the employee does not exist.
     */
    public String setGoal(Goal goal) {
        if (goal == null || goal.getEmployeeId() == null) {
            throw new IllegalArgumentException("Goal or Employee ID cannot be empty");
        }
        // Validate that the employee exists
        authUserRepository.findById(goal.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee Does Not Exist"));

        goalRepository.save(goal);
        return "Goal set successfully";
    }

    /**
     * Deletes a goal by its ID.
     *
     * @param goalId The ID of the goal to delete.
     */
    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }

    /**
     * Finds all goals associated with a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return A list of {@link Goal} objects belonging to the employee.
     */
    public List<Goal> findGoalsByEmployeeId(Long employeeId) {
        return goalRepository.findGoalsByEmployeeId(employeeId);
    }
}
