package com.example.wellnessportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.repository.SurveyRepository;

import com.example.wellnessportal.model.Survey;

@Service
public class SurveyService {
  
    @Autowired
    private SurveyRepository surveyRepository;

    // Admin Operations on Survey
    public Survey createSurvey(String surveyTitle, List<String> questions) 
    {
    Survey survey = new Survey(surveyTitle, 
                               questions);

    return surveyRepository.save(survey);
    }

    public Survey deleteSurvey(Long surveyId) 
    {
    Survey survey = surveyRepository.findById(surveyId).orElse(null);
    
    if (survey != null) 
    {
        surveyRepository.delete(survey);
    }
    
    return survey;
    }

    /* Search surveys based on:
     * 1. Survey Id
     * 2. Survey Title
     */
    public Survey getSurveyBySurveyId(Long surveyId)
    {
        return surveyRepository.findSurveyBySurveyId(surveyId);
    }

    public Survey getSurveyBySurveyTitle(String surveyTitle)
    {
        return surveyRepository.findSurveyBySurveyTitle(surveyTitle);
    }
    
}
