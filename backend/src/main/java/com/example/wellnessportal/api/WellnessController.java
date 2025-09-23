package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wellness")
public class WellnessController {
    @PostMapping
    public String submitMetrics() {
        // TODO: Implement submit metrics logic
        return "Submit wellness metrics endpoint";
    }

    @GetMapping
    public String viewMetrics() {
        // TODO: Implement view metrics logic
        return "View wellness metrics endpoint";
    }
}

