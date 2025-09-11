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

    Survey createSurvey(String surveyTitle, List<String> questions) 
    {
    Survey survey = new Survey(surveyTitle, 
                               questions);
    survey.setSurveyTitle(surveyTitle);
    survey.setQuestions(questions); // Set questions entered by admin
    return surveyRepository.save(survey);
    }

    Survey deleteSurvey(Long surveyId) 
    {
    Survey survey = surveyRepository.findById(surveyId).orElse(null);
    if (survey != null) 
    {
        surveyRepository.delete(survey);
    }
    return survey;
    }

    Survey getSurveyBySurveyId(Long surveyId)
    {
        return surveyRepository.findSurveyBySurveyId(surveyId);
    }
    
}
