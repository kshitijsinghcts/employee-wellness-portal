package com.example.wellnessportal.GenAI;

import com.example.wellnessportal.GenAI.ChatContext;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatContextService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    public ChatContext buildContext(Long userId, String role) {
        ChatContext context = new ChatContext();
        context.setEmployeeId(userId);
        context.setRole(role);

        if ("employee".equalsIgnoreCase(role)) {
            context.setWellnessMetrics(fetchWellnessMetrics(userId));
            context.setGoals(fetchGoals(userId));
            context.setResources(fetchResources());
            context.setSurveyResponses(fetchSurveyResponses(userId));
        } else if ("admin".equalsIgnoreCase(role)) {
            context.setResources(fetchResources());
            context.setSurveyResponses(fetchAllSurveySummaries());
        }

        return context;
    }

  private Map<String, Object> fetchWellnessMetrics(Long employeeId) {
    List<WellnessMetric> metrics = wellnessMetricRepository.findAllByEmployeeId(employeeId);

    Map<String, Object> result = new LinkedHashMap<>();

    for (WellnessMetric metric : metrics) {
        result.put("Date", metric.getRecordDate().toString());
        result.put("Mood", metric.getMood());
        result.put("Sleep Hours", metric.getSleepHours());
        result.put("Daily Steps", metric.getDailySteps());
        result.put("Water Intake", metric.getWaterIntake());

        if (metric.getScores() != null && !metric.getScores().isEmpty()) {
            result.put("Scores", metric.getScores());
        }
    }

    return result;
}


    private List<String> fetchGoals(Long employeeId) {
        return goalRepository.findGoalsByEmployeeId(employeeId).stream()
                .map(goal -> goal.getDescription())
                .collect(Collectors.toList());
    }

    private List<String> fetchResources() {
        return resourceRepository.findAll().stream()
                .map(resource -> resource.getTitle())
                .collect(Collectors.toList());
    }

   private List<String> fetchSurveyResponses(Long employeeId) {
    return surveyResponseRepository.findAll().stream()
        .filter(sr -> sr.getEmployeeId().equals(employeeId))
        .flatMap(sr -> sr.getAnswers().entrySet().stream()
            .map(entry -> "Q: " + entry.getKey() + " A: " + entry.getValue()))
        .collect(Collectors.toList());
}
  private List<String> fetchAllSurveySummaries() {
    return surveyResponseRepository.findAll().stream()
        .flatMap(sr -> sr.getAnswers().entrySet().stream()
            .map(entry -> "Q: " + entry.getKey() + " A: " + entry.getValue()))
        .collect(Collectors.toList());
}
}