package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.util.Map;

/**
 * Represents a single response to a survey submitted by an employee.
 * This is a JPA entity that maps to the 'survey_response' table.
 */
@Entity
public class SurveyResponse {
    /** A unique identifier for the survey response. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    private Long surveyId;
    private Long employeeId;

    /**
     * A map of questions to answers for the survey.
     * This is stored in a separate table `survey_answers` for data integrity.
     */
    @ElementCollection
    @CollectionTable(name = "survey_answers", joinColumns = @JoinColumn(name = "response_id"))
    @MapKeyColumn(name = "question")
    @Column(name = "answer")
    private Map<String, String> answers;

    /**
     * Default constructor required by JPA.
     */
    public SurveyResponse() {

    }

    /**
     * Constructs a new SurveyResponse.
     * Used by `SurveyResponsesService` when saving a new response.
     */
    public SurveyResponse(Long surveyId,
            Long employeeId,
            Map<String, String> answers) {
        this.surveyId = surveyId;
        this.employeeId = employeeId;
        this.answers = answers;
    }

    // --- Getters and Setters ---
    public Long getResponseId() {
        return responseId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}