package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s WHERE s.surveyId = :surveyId")
    Survey findSurveyBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT s FROM Survey s WHERE s.surveyTitle = :title")
    Survey findSurveyBySurveyTitle(@Param("title") String title);

    // Deletion by admin
   

  
}
