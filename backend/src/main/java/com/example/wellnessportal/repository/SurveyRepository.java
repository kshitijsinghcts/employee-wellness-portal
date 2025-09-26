package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Survey;

/**
 * Repository interface for Survey entity.
 * Provides access to survey-related data using Spring Data JPA.
 * 
 * <p><b>Service Layer:</b> Typically used in SurveyService to handle business logic.</p>
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    /**
     * Finds a survey by its unique survey ID.
     * 
     * @param surveyId the ID of the survey
     * @return Survey entity matching the given survey ID
     * 
     * <p><b>Query:</b> Custom JPQL query filtering by surveyId field</p>
     */
    @Query("SELECT s FROM Survey s WHERE s.surveyId = :surveyId")
    Survey findSurveyBySurveyId(@Param("surveyId") Long surveyId);

    /**
     * Finds a survey by its title using a case-insensitive partial match.
     * 
     * @param title the title or keyword to search for
     * @return Survey entity matching the title pattern
     * 
     * <p><b>Query:</b> Uses LIKE with LOWER for case-insensitive search</p>
     */
    @Query("SELECT s FROM Survey s WHERE LOWER(s.surveyTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
    Survey findSurveyBySurveyTitle(@Param("title") String title);

}