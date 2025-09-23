package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.WellnessMetricService;
import com.example.wellnessportal.model.WellnessMetric;

@RestController
@RequestMapping("/api/wellness")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend
public class WellnessController {

    @Autowired
    private WellnessMetricService wellnessMetricService;

    @PostMapping("/submit-metrics")
    public ResponseEntity<?> submitMetrics(@RequestBody WellnessMetric metrics) {
        try {
            WellnessMetric savedMetric = wellnessMetricService.saveWellnessMetric(metrics);
            return ResponseEntity.ok(savedMetric);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error submitting metrics: " + e.getMessage());
        }
    }

    @GetMapping("/{employeeId}")
    public List<WellnessMetric> viewMetrics(@PathVariable Long employeeId) {
        return wellnessMetricService.getEmployeeLogs(employeeId);
    }
}
