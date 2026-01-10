package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Client;
import com.pixiehex.kshipping.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody Client client) {
        if (clientRepository.findByMail(client.getMail()).isPresent()) {
            return "User already exists";
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return "User registered successfully";
    }
}
