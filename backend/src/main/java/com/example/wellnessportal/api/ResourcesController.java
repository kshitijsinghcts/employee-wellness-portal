package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {
    @GetMapping
    public String fetchResources() {
        // TODO: Implement fetch wellness content logic
        return "Fetch wellness resources endpoint";
    }
}