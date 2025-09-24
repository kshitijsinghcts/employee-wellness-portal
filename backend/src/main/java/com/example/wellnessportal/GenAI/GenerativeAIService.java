package com.example.wellnessportal.GenAI;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GenerativeAIService {

    private final String GENKIT_URL = "http://localhost:3400/chatFlow"; // Update if deployed

    public String getBotResponse(String prompt) {
        OkHttpClient client = new OkHttpClient();

        String json = "{ \"message\": \"" + prompt.replace("\"", "\\\"") + "\" }";

        RequestBody body = RequestBody.create(
            json, MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
            .url(GENKIT_URL)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Error: Genkit server returned " + response.code();
            }

            String responseBody = response.body().string();
            JsonNode root = new ObjectMapper().readTree(responseBody);
            return root.path("reply").asText(); // Adjust if your Genkit flow returns differently
        } catch (Exception e) {
            return "Error communicating with Genkit: " + e.getMessage();
        }
    }
}