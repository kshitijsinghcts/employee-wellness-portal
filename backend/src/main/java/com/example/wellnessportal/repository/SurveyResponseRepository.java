package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.SurveyResponse;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
   SurveyResponse findSurveyResponseBySurveyResponseId(Long surveyResponseId);
   SurveyResponse findSurveyResponseBySurveyIdAndEmployeeId(Long surveyId, Long employeeId);

}
