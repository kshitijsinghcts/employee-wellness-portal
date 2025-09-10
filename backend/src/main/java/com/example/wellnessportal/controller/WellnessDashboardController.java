package com.example.wellnessportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.repository.WellnessMetricRepository;

@RestController
@RequestMapping("/api/wellness")
public class WellnessDashboardController {

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    // Get welcome message
    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMessage(@RequestParam Long employeeId) 
    {
        // Ideally, fetch employee from DB
         // fallback
        // You can inject EmployeeService to fetch name
        return ResponseEntity.ok("Welcome " );
    }

    // Get mood metric
    @GetMapping("/mood")
    public ResponseEntity<String> getMood(@RequestParam Long employeeId) {
        String mood = wellnessMetricRepository.findLatestMoodByEmployeeId(employeeId);
        return ResponseEntity.ok(mood);
    }

    // Get sleep hours
    @GetMapping("/sleepHours")
    public ResponseEntity<Integer> getSleepHours(@RequestParam Long employeeId) {
        int sleepHours = wellnessMetricRepository.findLatestSleepHoursByEmployeeId(employeeId);
        return ResponseEntity.ok(sleepHours);
    }

    // Get daily steps
    @GetMapping("/dailySteps")
    public ResponseEntity<Integer> getDailySteps(@RequestParam Long employeeId) {
        int steps = wellnessMetricRepository.getDailyStepsByEmployeeId(employeeId);
        return ResponseEntity.ok(steps);
    }

    // Get water intake
    @GetMapping("/waterIntake")
    public ResponseEntity<Integer> getWater(@RequestParam Long employeeId) {
        int waterIntake = wellnessMetricRepository.getWaterIntakeByEmployeeId(employeeId);
        return ResponseEntity.ok(waterIntake);
    }

    // Get rewards
    @GetMapping("/rewards")
    public ResponseEntity<String> getRewards(@RequestParam Long employeeId) {
        String rewards = wellnessMetricRepository.findRewardsByEmployeeId(employeeId).toString();
        return ResponseEntity.ok(rewards);
    }
}
