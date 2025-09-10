package com.example.wellnessportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.repository.GoalRepository;

@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    // Set goal using JSON from Angular
    @PostMapping("/setGoal")
    public ResponseEntity<String> setGoal(@RequestBody Goal goal) 
    {
        goalRepository.save(goal);
        return ResponseEntity.ok("Goal set");
    }

    // Get goal by employee ID
    @GetMapping("/getGoal")
    public ResponseEntity<List<Goal>> getGoal(@RequestParam Long employeeId) 
    {
        List<Goal> goals = goalRepository.findGoalsByEmployeeId(employeeId);
        if (goals == null || goals.isEmpty()) 
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goals);
    }
}
