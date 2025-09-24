package com.example.wellnessportal.GenAI;

import com.example.wellnessportal.GenAI.ChatContext;
import org.springframework.stereotype.Service;

@Service
public class ChatPromptService {

    public String generatePrompt(ChatContext context, String userMessage) {
        StringBuilder prompt = new StringBuilder();

        // System role
        prompt.append("You are a wellness assistant helping a ")
              .append(context.getRole()).append(" in an employee wellness portal.\n");

        // User message
        prompt.append("User message: ").append(userMessage).append("\n\n");

        // Goals
        if (context.getGoals() != null && !context.getGoals().isEmpty()) {
            prompt.append("Current wellness goals:\n");
            context.getGoals().forEach(goal -> prompt.append("- ").append(goal).append("\n"));
            prompt.append("\n");
        }

        // Wellness Metrics
        if (context.getWellnessMetrics() != null && !context.getWellnessMetrics().isEmpty()) {
            prompt.append("Recent wellness metrics:\n");
            context.getWellnessMetrics().forEach((key, value) ->
                prompt.append("- ").append(key).append(": ").append(value).append("\n"));
            prompt.append("\n");
        }

        // Survey Responses
        if (context.getSurveyResponses() != null && !context.getSurveyResponses().isEmpty()) {
            prompt.append("Recent survey responses:\n");
            context.getSurveyResponses().forEach(resp ->
                prompt.append("- ").append(resp).append("\n"));
            prompt.append("\n");
        }

        // Resources
        if (context.getResources() != null && !context.getResources().isEmpty()) {
            prompt.append("Available wellness resources:\n");
            context.getResources().forEach(res ->
                prompt.append("- ").append(res).append("\n"));
            prompt.append("\n");
        }

        // Final instruction
        prompt.append("Based on the above context, respond helpfully to the user's message.");

        return prompt.toString();
    }
}