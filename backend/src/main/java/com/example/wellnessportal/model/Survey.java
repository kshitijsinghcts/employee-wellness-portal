package com.example.wellnessportal.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    private String surveyTitle;

    @ElementCollection
    private List<String> surveyQuestions;


    public Survey()
    {

    }
    
    public Survey(String surveyTitle,
            List<String> surveyQuestions) {
        this.surveyTitle = surveyTitle;
        this.surveyQuestions = surveyQuestions;
    }

    // Getters and Setters
    public Long getSurveyId() {
        return surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public List<String> getQuestions() {
        return surveyQuestions;
    }

    public void setQuestions(List<String> surveyQuestions) {
        this.surveyQuestions = surveyQuestions;
    }
}