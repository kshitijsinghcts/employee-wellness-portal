package com.example.wellnessportal.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.WellnessMetricService;
import com.example.wellnessportal.model.WellnessMetric;

@RestController
@RequestMapping("/api/wellness")
public class WellnessController {

    @Autowired
    private WellnessMetricService wellnessMetricService;

    @PostMapping("/submit-metrics")
    public String submitMetrics(@RequestBody WellnessMetric metrics) {
        // accepts: {id, mood, sleep, steps, water}
        try {
            wellnessMetricService.saveWellnessMetric(metrics);
            return "Metrics submitted successfully";
        } catch (Exception e) {
            return "Error submitting metrics: " + e.getMessage();
        }

    }

    @GetMapping("/{employeeId}")
    public List<WellnessMetric> viewMetrics(@PathVariable Long employeeId) {
        return wellnessMetricService.getEmployeeLogs(employeeId);
    }
}
