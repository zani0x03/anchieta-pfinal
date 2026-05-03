package com.anchieta.login.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.anchieta.login.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final com.anchieta.login.service.KeycloakService keycloakService;

    public AuthController(com.anchieta.login.service.KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Value("${keycloak.server-url}/realms/${keycloak.realm}/protocol/openid-connect/token")
    private String keycloakUrl;

    @Value("${keycloak.student-client-id:student-app}")
    private String clientId;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String sistemaId = credentials.getSistemaId();

        if (sistemaId == null || username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and sistemaId are required.");
        }

        String compositeUsername = sistemaId + "_" + username;
        
        System.out.println("Tentando login com Client: " + clientId);
        System.out.println("Username Final: " + compositeUsername);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("username", compositeUsername);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(keycloakUrl, request, Map.class);

            // Validate system association
            if (!keycloakService.validateUserSistema(compositeUsername, sistemaId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado para este sistema.");
            }

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error connecting to Keycloak: " + e.getMessage());
        }
    }

}
