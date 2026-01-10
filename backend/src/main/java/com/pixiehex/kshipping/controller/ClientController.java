package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Client;
import com.pixiehex.kshipping.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    public ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok(new Client());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        return ResponseEntity.ok(new Client());
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody Client Client) {
        return ResponseEntity.ok(new Client());
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Client Client, @PathVariable int id) {
        return ResponseEntity.ok(new Client());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(new Client());
    }

}
