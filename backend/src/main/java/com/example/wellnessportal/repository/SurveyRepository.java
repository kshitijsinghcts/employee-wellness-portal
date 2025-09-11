package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {


    @Query("SELECT s FROM Survey s WHERE s.surveyId = :surveyId")
    Survey findSurveyBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT s FROM Survey s WHERE s.surveyTitle = :title")
    Survey findSurveyBySurveyTitle(@Param("title") String title);
    
    //Deletion by admin
    @Query("DELETE FROM Survey s WHERE s.surveyId = :surveyId")
    Survey deleteSurveyBySurveyId(@Param("surveyId") Long surveyId);
   
    @Query("UPDATE Survey s SET s.status = 'Updated' WHERE s.surveyId = :surveyId OR s.surveyTitle = :surveyTitle")
    void updateSurveyBySurveyIdOrSurveyTitle(@Param("surveyId") Long surveyId, @Param("surveyTitle") String surveyTitle);
}


