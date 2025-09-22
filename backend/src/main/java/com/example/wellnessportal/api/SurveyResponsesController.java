package com.example.wellnessportal.api;

import com.example.wellnessportal.model.SurveyResponse;
import com.example.wellnessportal.service.SurveyResponsesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/survey-responses")
public class SurveyResponsesController {

    @Autowired
    private SurveyResponsesService surveyResponsesService;

    // Employee: Save a survey response
    @PostMapping("/submit")
    public SurveyResponse submitSurveyResponse(@RequestParam Long surveyId,
                                               @RequestParam Long employeeId,
                                               @RequestBody Map<String, String> answers) {
        return surveyResponsesService.saveSurveyResponse(surveyId, employeeId, answers);
    }

    // Admin: Get a specific survey response by surveyId and employeeId
    @GetMapping("/by-survey-and-employee")
    public SurveyResponse getSurveyResponseBySurveyAndEmployee(@RequestParam Long surveyId,
                                                               @RequestParam Long employeeId) {
        return surveyResponsesService.getSurveyResponseBySurveyId(surveyId, employeeId);
    }

    // Admin: Get all responses for a survey
    @GetMapping("/by-survey")
    public List<SurveyResponse> getResponsesBySurvey(@RequestParam Long surveyId) {
        return surveyResponsesService.getSurveyResponseBySurveyId(surveyId);
    }

    // Admin: Get a specific response by employee
    @GetMapping("/by-employee")
    public SurveyResponse getResponseByEmployee(@RequestParam Long surveyId,
                                                @RequestParam Long employeeId) {
        return surveyResponsesService.getSurveyResponseByEmployeeId(surveyId, employeeId);
    }

    // Employee/Admin: Delete a survey response
    @DeleteMapping("/delete")
    public void deleteSurveyResponse(@RequestParam Long surveyId,
                                     @RequestParam Long employeeId) {
        surveyResponsesService.deleteSurveyResponse(surveyId, employeeId);
    }

    // Employee/Admin: Edit a survey response
    @PutMapping("/edit")
    public void editSurveyResponse(@RequestParam Long surveyId,
                                   @RequestParam Long employeeId,
                                   @RequestBody Map<String, String> newAnswers) {
        surveyResponsesService.editSurveyResponse(surveyId, employeeId, newAnswers);
    }
}
