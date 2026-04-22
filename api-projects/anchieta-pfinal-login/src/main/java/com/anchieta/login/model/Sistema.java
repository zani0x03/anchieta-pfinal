package com.anchieta.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "sistemas")
public class Sistema {
    @Id
    private UUID id;
    private String nome;

    public Sistema() {}

    public Sistema(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
