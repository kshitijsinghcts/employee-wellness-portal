package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
   public Survey findSurveyBySurveyId(Long surveyId);
   public Survey findSurveyBySurveyTitle(String title);
   //For admin to delete by Survey Id
   public Survey deleteSurveyBySurveyId(Long surveyId);
   //For admin to update the survey status 
   public void updateSurveyBySurveyIdOrSurveyTitle(Long surveyId, String surveyTitle);

}
