package com.anchieta.login.dto;

public class LoginRequest {
    private String username;
    private String password;
    private String sistemaId;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(String sistemaId) {
        this.sistemaId = sistemaId;
    }
}
