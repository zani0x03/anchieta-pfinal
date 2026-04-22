package com.anchieta.login.dto;

import java.util.UUID;

public class RegistrationRequest {
    private String name;
    private String surname;
    private String login;
    private String email;
    private String password;
    private UUID sistemaId;

    public RegistrationRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UUID getSistemaId() { return sistemaId; }
    public void setSistemaId(UUID sistemaId) { this.sistemaId = sistemaId; }
}
