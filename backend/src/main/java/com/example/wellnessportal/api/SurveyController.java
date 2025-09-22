// package com.example.wellnessportal.api;

// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/surveys")
// public class SurveysController {
//     @GetMapping
//     public String getSurveys() {
//         // TODO: Implement get surveys logic
//         return "Get surveys endpoint";
//     }

//     @PostMapping("/responses")
//     public String submitSurveyResponse() {
//         // TODO: Implement submit survey response logic
//         return "Submit survey response endpoint";
//     }
// }

package com.example.wellnessportal.api;

import com.example.wellnessportal.model.Survey;
import com.example.wellnessportal.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // Create a new survey
    @PostMapping("/create")
    public Survey createSurvey(@RequestBody Map<String, Object> request) {
        String surveyTitle = (String) request.get("surveyTitle");
        @SuppressWarnings("unchecked")
        List<String> questions = (List<String>) request.get("questions");
        return surveyService.createSurvey(surveyTitle, questions);
    }

    // Delete a survey
    @DeleteMapping("/delete")
    public Survey deleteSurvey(@RequestBody Map<String, Long> request) {
        Long surveyId = request.get("surveyId");
        return surveyService.deleteSurvey(surveyId);
    }

    // Get survey by ID
    @PostMapping("/getById")
    public Survey getSurveyById(@RequestBody Map<String, Long> request) {
        Long surveyId = request.get("surveyId");
        return surveyService.getSurveyBySurveyId(surveyId);
    }

    // Get survey by title
    @PostMapping("/getByTitle")
    public Survey getSurveyByTitle(@RequestBody Map<String, String> request) {
        String surveyTitle = request.get("surveyTitle");
        return surveyService.getSurveyBySurveyTitle(surveyTitle);
    }
}
