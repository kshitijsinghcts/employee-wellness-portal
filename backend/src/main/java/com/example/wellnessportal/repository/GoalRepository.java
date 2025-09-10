package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
         List<Goal> findGoalsByEmployeeId(Long employeeId);
}
