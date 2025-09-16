package com.example.wellnessportal.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.SurveyResponse;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {


    @Query("SELECT sr FROM SurveyResponse sr WHERE sr.surveyId = :surveyId")
    List<SurveyResponse> findSurveyResponseBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT sr FROM SurveyResponse sr WHERE sr.surveyId = :surveyId AND sr.employeeId = :employeeId")
    SurveyResponse findSurveyResponseBySurveyIdAndEmployeeId(@Param("surveyId") Long surveyId,
            @Param("employeeId") Long employeeId);

    @Query("UPDATE SurveyResponse sr SET sr.answers = :newAnswers WHERE sr.surveyId = :surveyId OR sr.employeeId= :employeeId")
    void updateSurveyBySurveyIdOrSurveyTitle(@Param("surveyId") Long surveyId,
                                              @Param("employeeId") Long employeeId,
                                              @Param("newAnswers") Map<String, String> newAnswers);
}
