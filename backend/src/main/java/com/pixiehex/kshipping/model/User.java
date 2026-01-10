package com.pixiehex.kshipping.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class User {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    @Email
    private String mail;
    @NotNull
    private String password;

}
