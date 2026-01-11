package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Client;
import com.pixiehex.kshipping.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllUsers() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getUser(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> addUser(@Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.registerClient(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateUser(@Valid @RequestBody Client client, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientService.updateClient(id, client));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }


}