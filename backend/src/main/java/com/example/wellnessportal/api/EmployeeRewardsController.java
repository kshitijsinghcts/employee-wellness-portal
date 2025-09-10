package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employeeRewards")
public class EmployeeRewardsController {
    @PostMapping
    public String assignReward() {
        // TODO: Implement assign reward logic
        return "Assign reward endpoint";
    }
}
