package com.pixiehex.kshipping.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Client {
    public @NotNull Long getClientId() {
        return clientId;
    }


    public @Email String getMail() {
        return mail;
    }

    public void setMail(@Email String mail) {
        this.mail = mail;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    @Email
    private String mail;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }

}


