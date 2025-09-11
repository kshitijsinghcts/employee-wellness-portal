package com.example.wellnessportal.service;

import java.util.Map;
import java.util.List;

import com.example.wellnessportal.model.SurveyResponse;
import com.example.wellnessportal.repository.SurveyResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyResponsesService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    // The following operation can be performed by employee only
    public SurveyResponse saveSurveyResponse(Long surveyId,
            Long employeeId,
            Map<String, String> answers) {
        SurveyResponse surveyResponse = new SurveyResponse(surveyId,
                employeeId,
                answers);
        return surveyResponseRepository.save(surveyResponse);
    }

    /*
     * The following operations can only be performed by admin
     * He/she can perform one of the following operations:
     * 1. View survey response by survey id
     * 2. View survey response by employee id
     * 3. View all survey responses based on survey id
     */
    public SurveyResponse getSurveyResponseBySurveyId(Long surveyId, Long employeeId) {
        return surveyResponseRepository.findSurveyResponseBySurveyIdAndEmployeeId(surveyId, employeeId);
    }

    public List<SurveyResponse> getSurveyResponseBySurveyId(Long surveyId) {
        return surveyResponseRepository.findSurveyResponseBySurveyId(surveyId);
    }

    public SurveyResponse getSurveyResponseByEmployeeId(Long surveyId, Long employeeId) {
        return surveyResponseRepository.findSurveyResponseBySurveyIdAndEmployeeId(surveyId, employeeId);
    }

    // The following operations can be performed by both employee and admin
    public void deleteSurveyResponse(Long surveyId, Long employeeId) {
        SurveyResponse response = getSurveyResponseBySurveyId(surveyId, employeeId);
        if (response != null) {
            surveyResponseRepository.delete(response);
        }
    }

    public void editSurveyResponse(Long surveyId,
            Long employeeId,
            Map<String, String> newAnswers) {
        SurveyResponse response = getSurveyResponseBySurveyId(surveyId, employeeId);
        if (response != null) {
            response.setAnswers(newAnswers);
            surveyResponseRepository.save(response);
        } else {
            //Handle in the controller
            throw new IllegalArgumentException("Survey response not found for the given survey ID and employee ID.");
        }
    }

}
