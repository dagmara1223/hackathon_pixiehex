package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.model.Client;
import com.pixiehex.kshipping.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client registerClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    existingClient.setMail(updatedClient.getMail());
                    existingClient.setPassword(updatedClient.getPassword());
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Klient o id " + id + " nie istnieje"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}