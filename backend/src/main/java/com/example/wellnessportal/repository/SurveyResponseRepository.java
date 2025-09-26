package com.example.wellnessportal.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.SurveyResponse;

/**
 * Repository interface for SurveyResponse entity.
 * Provides access to survey response data using Spring Data JPA.
 * 
 * <p><b>Service Layer:</b> Typically used in SurveyResponseService to handle business logic.</p>
 */
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    /**
     * Retrieves all survey responses associated with a specific survey ID.
     * 
     * @param surveyId the ID of the survey
     * @return list of SurveyResponse entities linked to the given survey
     */
    @Query("SELECT sr FROM SurveyResponse sr WHERE sr.surveyId = :surveyId")
    List<SurveyResponse> findSurveyResponseBySurveyId(@Param("surveyId") Long surveyId);

    /**
     * Retrieves a specific survey response submitted by an employee for a given survey.
     * 
     * @param surveyId the ID of the survey
     * @param employeeId the ID of the employee
     * @return SurveyResponse entity matching both survey and employee IDs
     */
    @Query("SELECT sr FROM SurveyResponse sr WHERE sr.surveyId = :surveyId AND sr.employeeId = :employeeId")
    SurveyResponse findSurveyResponseBySurveyIdAndEmployeeId(@Param("surveyId") Long surveyId,
                                                             @Param("employeeId") Long employeeId);

    /**
     * Updates the answers of a survey response based on either survey ID or employee ID.
     * 
     * <p><b>Note:</b> This query is written as JPQL but will not work as expected because JPQL does not support updates with complex types like Map directly.</p>
     * 
     * <p><b>Recommendation:</b> Use native queries or handle updates via service logic instead.</p>
     * 
     * @param surveyId the ID of the survey
     * @param employeeId the ID of the employee
     * @param newAnswers the new answers to be updated
     */
    @Query("UPDATE SurveyResponse sr SET sr.answers = :newAnswers WHERE sr.surveyId = :surveyId OR sr.employeeId= :employeeId")
    void updateSurveyBySurveyIdOrSurveyTitle(@Param("surveyId") Long surveyId,
                                             @Param("employeeId") Long employeeId,
                                             @Param("newAnswers") Map<String, String> newAnswers);
}