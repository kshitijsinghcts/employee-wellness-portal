package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Goal;

/**
 * Repository interface for Goal entity.
 * Provides access to goal-related data using Spring Data JPA.
 * 
 * <p><b>Service Layer:</b> Typically used in GoalService to handle business logic.</p>
 */
public interface GoalRepository extends JpaRepository<Goal, Long> {

    /**
     * Retrieves all goals associated with a specific employee ID.
     * 
     * @param employeeId the employee ID to filter goals
     * @return a list of Goal entities linked to the given employee ID
     * 
     * <p><b>Query:</b> Custom JPQL query filtering by employeeId field</p>
     */
    @Query("SELECT g FROM Goal g WHERE g.employeeId = :employeeId")
    List<Goal> findGoalsByEmployeeId(@Param("employeeId") Long employeeId);
}