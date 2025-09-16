package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Goal;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {
    // accepts {employeeId:..., title:..., description:..., targetDate:...}
    @PostMapping
    public String createGoal(@RequestBody Goal goal) {
        return "Create goal endpoint";
    }

    @PutMapping
    public String updateGoal() {
        // TODO: Implement update goal logic
        return "Update goal endpoint";
    }

    @GetMapping
    public String viewGoals() {
        // TODO: Implement view goals logic
        return "View goals endpoint";
    }
}
