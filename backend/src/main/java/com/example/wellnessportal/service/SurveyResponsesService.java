package com.example.wellnessportal.service;


import java.util.Map;

import com.example.wellnessportal.model.SurveyResponse;
import com.example.wellnessportal.repository.SurveyResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyResponsesService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

 
    SurveyResponse saveSurveyResponse(Long surveyId, 
                                      Long employeeId, 
                                      Map<String, String> answers) 
    {
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
        for (SurveyResponse response : surveyResponseRepository.findAll()) {
            if (response.getSurveyId().equals(surveyId) && response.getEmployeeId().equals(employeeId)) {
                return response;
            }
        }
        return null;
    }

    public List<SurveyResponse> getSurveyResponseBySurveyId(Long surveyId) {
        List<SurveyResponse> responses = new java.util.ArrayList<>();
        for (SurveyResponse response : surveyResponseRepository.findAll()) {
            if (response.getSurveyId().equals(surveyId)) {
                responses.add(response);
            }
        }
        return responses;
    }
    
    public SurveyResponse getSurveyResponseByEmployeeId(Long employeeId) {
        for (SurveyResponse response : surveyResponseRepository.findAll()) {
            if (response.getEmployeeId().equals(employeeId)) {
                return response;
            }
        }
        return null;
    }
    
    // Can be performed by both employee and admin
    public void deleteSurveyResponse(Long surveyId, Long employeeId) 
    {
        SurveyResponse response = getSurveyResponseBySurveyId(surveyId, employeeId);
        if (response != null) 
        {
            surveyResponseRepository.delete(response);
        }
    }

    public void editSurveyResponse(Long surveyId, 
                                   Long employeeId, 
                                   Map<String, String> newAnswers) 
    {
        SurveyResponse response = getSurveyResponseBySurveyId(surveyId, employeeId);
        if (response != null) 
        {
            response.setAnswers(newAnswers);
            surveyResponseRepository.save(response);
        } 
        else 
        {
            throw new IllegalArgumentException("Survey response not found for the given survey ID and employee ID.");
        }
    }




    
}
