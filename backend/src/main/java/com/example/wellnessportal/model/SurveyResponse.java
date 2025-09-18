package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.util.Map;

@Entity
public class SurveyResponse {
//Mapped to Survey Object
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    private Long surveyId;
    private Long employeeId;

    // Separate Table named survey_answers with question and answer as columns for data integrity
    @ElementCollection
    @CollectionTable(name = "survey_answers", joinColumns = @JoinColumn(name = "response_id"))
    @MapKeyColumn(name = "question")
    @Column(name = "answer")
    private Map<String, String> answers;

    // Constructors
    public SurveyResponse()
    {

    }
    
    public SurveyResponse(Long surveyId, 
                          Long employeeId, 
                          Map<String, String> answers) 
    {
        this.surveyId = surveyId;
        this.employeeId = employeeId;
        this.answers = answers;
    }

    // Getters and Setters
    public Long getResponseId() 
    { 
        return responseId; 
    }

    public Long getSurveyId() 
    {
         return surveyId;
         }
    public void setSurveyId(Long surveyId) 
    { 
        this.surveyId = surveyId; 
    }

    public Long getEmployeeId() 
    { 
        return employeeId; 
    }
    public void setEmployeeId(Long employeeId) 
    { 
        this.employeeId = employeeId; 
    }

    public Map<String, String> getAnswers() 
    { 
        return answers; 
    }
    public void setAnswers(Map<String, String> answers) 
    { 
        this.answers = answers; 
    }
}