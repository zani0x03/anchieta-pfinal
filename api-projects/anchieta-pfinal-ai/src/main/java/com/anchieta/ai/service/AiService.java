package com.anchieta.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    @Value("${ollama.url:http://ollama:11434/api/generate}")
    private String ollamaUrl;

    @Value("${ollama.model:qwen:1.5b}")
    private String modelName;

    private final RestTemplate restTemplate;

    public AiService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(300000); // 5 minutos em milissegundos
        this.restTemplate = new RestTemplate(factory);
    }

    public String chat(String prompt) {
        String systemPrompt = "Você é um assistente educativo para estudantes. " +
                "REGRAS IMPORTANTES: " +
                "1. Não responda sobre política, religião ou temas polêmicos. " +
                "2. Se o usuário insistir nesses temas, diga que seu foco é apenas educativo e encerre o assunto de forma gentil. " +
                "3. Seja conciso e use linguagem amigável. " +
                "Pergunta do aluno: ";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", modelName);
        body.put("prompt", systemPrompt + prompt);
        body.put("stream", false);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(ollamaUrl, entity, Map.class);
            if (response != null && response.containsKey("response")) {
                return (String) response.get("response");
            }
            return "A IA pensou demais e acabou se perdendo no caminho! Tente novamente.";
        } catch (Exception e) {
            System.err.println("Timeout ou Erro na IA: " + e.getMessage());
            return "O tempo esgotou! Como dizem por aí: 'O silêncio é uma resposta, mas o professor prefere que você pergunte de novo mais tarde'.";
        }
    }
}
