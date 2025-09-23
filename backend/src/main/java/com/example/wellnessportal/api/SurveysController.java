package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveysController {
    @GetMapping
    public String getSurveys() {
        // TODO: Implement get surveys logic
        return "Get surveys endpoint";
    }

    @PostMapping("/responses")
    public String submitSurveyResponse() {
        // TODO: Implement submit survey response logic
        return "Submit survey response endpoint";
    }
}
