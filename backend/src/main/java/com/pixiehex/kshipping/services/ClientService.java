package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.repository.ClientRepository;

public class ClientService {
    public ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
}
