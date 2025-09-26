package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
        @Query("SELECT g FROM Goal g WHERE g.employeeId = :employeeId")
        List<Goal> findGoalsByEmployeeId(@Param("employeeId") Long employeeId);
}
