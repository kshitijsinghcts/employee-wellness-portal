package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {
    @PostMapping
    public String createGoal() {
        // TODO: Implement create goal logic
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
