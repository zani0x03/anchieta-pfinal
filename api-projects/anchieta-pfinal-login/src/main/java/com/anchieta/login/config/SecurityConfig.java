package com.anchieta.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Anchieta Login API").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Se aplicável para login
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // Definindo explicitamente os servidores
                .servers(Arrays.asList(
                        // Servidor local para desenvolvimento
                        new Server().url("http://localhost:8080"), // Ou a porta que o spring boot usa localmente
                        // Servidor externo via túnel/ingress
                        new Server().url("https://mobile-ios-login.zani0x03.eti.br")
                ));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Permitir login e acesso público
                .requestMatchers("/api/register").permitAll() // Permitir registro
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir acesso ao Swagger
                .anyRequest().authenticated() // Todas as outras requisições precisam de autenticação
            );
        return http.build();
    }
}
