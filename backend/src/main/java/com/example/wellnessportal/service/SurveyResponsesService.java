package com.example.wellnessportal.service;

import java.util.Map;
import java.util.List;

import com.example.wellnessportal.model.SurveyResponse;
import com.example.wellnessportal.repository.SurveyResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing survey responses.
 * Handles saving, retrieving, and deleting survey responses.
 * This service is used by the `SurveysController`.
 */
@Service
public class SurveyResponsesService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    /**
     * Saves a new survey response submitted by an employee.
     * Called from `SurveysController.submitSurveyResponse`.
     *
     * @param surveyId   The ID of the survey being responded to.
     * @param employeeId The ID of the employee submitting the response.
     * @param answers    A map of questions to answers provided by the employee.
     * @return The saved {@link SurveyResponse} object.
     */
    public SurveyResponse saveSurveyResponse(Long surveyId,
            Long employeeId,
            Map<String, String> answers) {
        SurveyResponse surveyResponse = new SurveyResponse(surveyId,
                employeeId,
                answers);
        return surveyResponseRepository.save(surveyResponse);
    }

    /**
     * Retrieves a specific survey response for a given survey and employee.
     * This is typically used by an admin to view a single submission.
     *
     * @param surveyId   The ID of the survey.
     * @param employeeId The ID of the employee.
     * @return The {@link SurveyResponse} object if found, otherwise null.
     */
    public SurveyResponse getSurveyResponseBySurveyId(Long surveyId, Long employeeId) {
        return surveyResponseRepository.findSurveyResponseBySurveyIdAndEmployeeId(surveyId, employeeId);
    }

    /**
     * Retrieves all responses for a specific survey.
     * This is used by an admin to see all submissions for a particular survey.
     *
     * @param surveyId The ID of the survey.
     * @return A list of all {@link SurveyResponse} objects for the given survey.
     */
    public List<SurveyResponse> getSurveyResponseBySurveyId(Long surveyId) {
        return surveyResponseRepository.findSurveyResponseBySurveyId(surveyId);
    }

    /**
     * Deletes a specific survey response.
     * This can be used by an employee to retract their response or by an admin for
     * management purposes.
     *
     * @param surveyId   The ID of the survey.
     * @param employeeId The ID of the employee whose response is to be deleted.
     */
    public void deleteSurveyResponse(Long surveyId, Long employeeId) {
        SurveyResponse response = getSurveyResponseBySurveyId(surveyId, employeeId);

        if (response != null) {
            surveyResponseRepository.delete(response);
        }
    }

}
