package com.pixiehex.kshipping.security;

import com.pixiehex.kshipping.model.Client;
import com.pixiehex.kshipping.repository.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Client client = clientRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(client.getMail())
                .password(client.getPassword())
                .roles("USER")
                .build();
    }
}