package com.anchieta.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    @Value("${ollama.url:http://ollama:11434/api/generate}")
    private String ollamaUrl;

    @Value("${ollama.model:qwen:1.5b}")
    private String modelName;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", modelName);
        body.put("prompt", prompt);
        body.put("stream", false);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(ollamaUrl, entity, Map.class);
            if (response != null && response.containsKey("response")) {
                return (String) response.get("response");
            }
            return "No response from AI engine.";
        } catch (Exception e) {
            return "Error calling AI engine: " + e.getMessage();
        }
    }
}
