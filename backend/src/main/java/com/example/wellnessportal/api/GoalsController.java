package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.service.GoalService;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/create")
    public String createGoal(@RequestBody Goal goal) {
        return goalService.setGoal(goal);
    }

    @PutMapping
    public String updateGoal() {
        // TODO: Implement update goal logic
        return "Update goal endpoint";
    }

    @GetMapping("/{employeeId}")
    public List<Goal> viewGoals(@PathVariable Long employeeId) {
        return goalService.findGoalsByEmployeeId(employeeId);
    }
}
