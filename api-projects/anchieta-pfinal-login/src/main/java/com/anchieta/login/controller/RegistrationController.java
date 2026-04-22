package com.anchieta.login.controller;

import com.anchieta.login.dto.RegistrationRequest;
import com.anchieta.login.repository.SistemaRepository;
import com.anchieta.login.service.KeycloakService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final SistemaRepository sistemaRepository;
    private final KeycloakService keycloakService;

    public RegistrationController(SistemaRepository sistemaRepository, KeycloakService keycloakService) {
        this.sistemaRepository = sistemaRepository;
        this.keycloakService = keycloakService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        // 3.3.1 Validate provided sistema_id
        if (!sistemaRepository.existsById(request.getSistemaId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sistema_id");
        }

        try {
            // 3.3.2 & 3.3.3 Create user and update attributes
            String userId = keycloakService.createUser(
                    request.getLogin(),
                    request.getEmail(),
                    request.getName(),
                    request.getSurname(),
                    request.getPassword(),
                    request.getSistemaId().toString()
            );
            // Return a generic success message instead of the userId
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
