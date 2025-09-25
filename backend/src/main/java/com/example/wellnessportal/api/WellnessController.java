package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.WellnessMetricService;
import com.example.wellnessportal.model.WellnessMetric;

/**
 * REST controller for managing employee wellness metrics.
 * Provides endpoints for submitting and viewing wellness data.
 */
@RestController
@RequestMapping("/api/wellness")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend
public class WellnessController {

    @Autowired
    private WellnessMetricService wellnessMetricService;

    /**
     * Submits wellness metrics for an employee.
     * 
     * @param metrics A JSON object in the request body with wellness data.
     *                Example: `{ "employeeId": 1, "mood": "Happy", "sleepHours": 8,
     *                "dailySteps": 10000, "waterIntake": 8 }`
     * @return A ResponseEntity containing the saved WellnessMetric on success
     *         (status 200 OK),
     *         or an error message on failure (status 400 Bad Request).
     */
    @PostMapping("/submit-metrics")
    public ResponseEntity<?> submitMetrics(@RequestBody WellnessMetric metrics) {
        try {
            WellnessMetric savedMetric = wellnessMetricService.saveWellnessMetric(metrics);
            return ResponseEntity.ok(savedMetric);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error submitting metrics: " + e.getMessage());
        }
    }

    /**
     * Retrieves all wellness metric logs for a specific employee.
     * 
     * @param employeeId The ID of the employee.
     * @return A ResponseEntity containing a list of WellnessMetric objects and HTTP
     *         status 200 (OK).
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<WellnessMetric>> viewMetrics(@PathVariable Long employeeId) {
        return ResponseEntity.ok(wellnessMetricService.getEmployeeLogs(employeeId));
    }
}
