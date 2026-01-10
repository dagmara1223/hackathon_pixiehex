package com.pixiehex.kshipping.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    @Email
    private String mail;
    @NotNull
    private String password;

}


