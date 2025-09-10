package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genai")
public class GenAIController {
    @GetMapping
    public String getAITips() {
        // TODO: Implement AI-generated tips/summary logic
        return "Get AI-generated tips/summary endpoint";
    }
}
