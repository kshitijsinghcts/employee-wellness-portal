package com.example.wellnessportal.GenAI;

import java.util.*;

public class ChatContext 
{
    private Long employeeId;
    private String role; // "admin" or "employee"
    private List<ChatMessage> history = new ArrayList<>();

    // Domain-specific context
    private Map<String, Object> wellnessMetrics = new HashMap<>();
    private List<String> goals = new ArrayList<>();
    private List<String> resources = new ArrayList<>();
    private List<String> surveyResponses = new ArrayList<>();

    // Getters and Setters
    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public String getRole() 
    {
        return role;
    }

    public void setRole(String role) 
    {
        this.role = role;
    }

    public List<ChatMessage> getHistory() 
    {
        return history;
    }

    public void addMessage(String role, String content) 
    {
        this.history.add(new ChatMessage(role, content));
    }

    public Map<String, Object> getWellnessMetrics() 
    {
        return wellnessMetrics;
    }

    public void setWellnessMetrics(Map<String, Object> wellnessMetrics) {
        this.wellnessMetrics = wellnessMetrics;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public List<String> getSurveyResponses() {
        return surveyResponses;
    }

    public void setSurveyResponses(List<String> surveyResponses) {
        this.surveyResponses = surveyResponses;
    }
}