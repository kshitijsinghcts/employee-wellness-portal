package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.service.GoalService;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class GoalsController {

    @Autowired
    private GoalService goalService;

    // accepts : {employeeId, title, description, targetDate, targetValue}
    @PostMapping("/create")
    public ResponseEntity<String> createGoal(@RequestBody Goal goal) {
        try {
            String result = goalService.setGoal(goal);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // accepts : {goalId, employeeId, title, description, targetDate, status,
    // targetValue}
    @PutMapping("/update")
    public ResponseEntity<String> updateGoal(@RequestBody Goal goal) {
        try {
            String result = goalService.setGoal(goal); // save can be used for update
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{employeeId:\\d+}")
    public List<Goal> viewGoals(@PathVariable Long employeeId) {
        return goalService.findGoalsByEmployeeId(employeeId);
    }

    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}
