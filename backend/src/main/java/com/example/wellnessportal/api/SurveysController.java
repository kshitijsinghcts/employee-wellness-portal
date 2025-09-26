package com.example.wellnessportal.api;

import com.example.wellnessportal.model.Survey;
import com.example.wellnessportal.model.SurveyResponse;
import com.example.wellnessportal.service.SurveyResponsesService;
import com.example.wellnessportal.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveysController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyResponsesService surveyResponsesService;

    /**
     * Retrieves a survey by its ID.
     *
     * @param id The ID of the survey to retrieve.
     * @return A ResponseEntity containing the Survey if found (status 200 OK),
     *         or a 404 Not Found response.
     */
    @GetMapping
    public ResponseEntity<Survey> getSurveyById(@RequestParam Long id) {
        Survey survey = surveyService.getSurveyBySurveyId(id);
        if (survey != null) {
            return ResponseEntity.ok(survey);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Submits a response for a survey.
     *
     * @param surveyResponse A JSON object in the request body representing the
     *                       survey response.
     *                       Example: `{ "surveyId": 1, "employeeId": 1, "answers":
     *                       { "question1": "answer1" } }`
     * @return A ResponseEntity containing the saved SurveyResponse and HTTP status
     *         201 (Created).
     */
    @PostMapping("/responses")
    public ResponseEntity<SurveyResponse> submitSurveyResponse(@RequestBody SurveyResponse surveyResponse) {
        SurveyResponse savedResponse = surveyResponsesService.saveSurveyResponse(
                surveyResponse.getSurveyId(),
                surveyResponse.getEmployeeId(),
                surveyResponse.getAnswers());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResponse);
    }
}
