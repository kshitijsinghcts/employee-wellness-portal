package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.service.GoalService;

/**
 * REST controller for managing employee goals.
 * Provides endpoints for creating, updating, viewing, and deleting goals.
 */
@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class GoalsController {

    @Autowired
    private GoalService goalService;

    /**
     * Creates a new goal for an employee.
     * 
     * @param goal A JSON object in the request body representing the goal.
     *             Example: `{ "employeeId": 1, "title": "Run 5k", "description":
     *             "Complete a 5k run", "targetDate": "2023-12-31", "targetValue":
     *             "5" }`
     * @return A ResponseEntity with a success message and status 201 (Created),
     *         or an error message with status 400 (Bad Request) if the input is
     *         invalid.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createGoal(@RequestBody Goal goal) {
        try {
            String result = goalService.setGoal(goal);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Updates an existing goal. The same service method for setting a goal is used
     * for updates.
     * 
     * @param goal A JSON object in the request body with the goal's updated
     *             information. Must include the `goalId`.
     *             Example: `{ "goalId": 1, "status": 1, "description": "Completed a
     *             5k run in 30 mins" }`
     * @return A ResponseEntity with a success message and status 200 (OK), or an
     *         error message with status 400 (Bad Request).
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateGoal(@RequestBody Goal goal) {
        try {
            String result = goalService.setGoal(goal); // save can be used for update
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves all goals for a specific employee.
     * 
     * @param employeeId The ID of the employee whose goals are to be retrieved.
     * @return A list of Goal objects.
     */
    @GetMapping("/{employeeId:\\d+}")
    public List<Goal> viewGoals(@PathVariable Long employeeId) {
        return goalService.findGoalsByEmployeeId(employeeId);
    }

    /**
     * Deletes a goal by its ID.
     * 
     * @param goalId The ID of the goal to be deleted.
     * @return A ResponseEntity with HTTP status 204 (No Content) on successful
     *         deletion.
     */
    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}
