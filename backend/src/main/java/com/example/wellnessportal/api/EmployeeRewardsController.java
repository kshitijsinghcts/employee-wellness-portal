package com.example.wellnessportal.api;

import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling employee rewards.
 * Provides an endpoint to retrieve all rewards for a specific employee.
 */
@RestController
@RequestMapping("/api/employeeRewards")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class EmployeeRewardsController {

    @Autowired
    private RewardsService rewardsService;

    /**
     * Retrieves all rewards earned by a given employee.
     * 
     * @param employeeId The ID of the employee.
     * @return A ResponseEntity containing a list of Rewards and HTTP status 200
     *         (OK).
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<Rewards>> getRewardsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(rewardsService.getRewardsForEmployee(employeeId));
    }
}
