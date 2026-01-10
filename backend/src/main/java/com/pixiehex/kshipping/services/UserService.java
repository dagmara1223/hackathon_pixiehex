package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.repository.UserRepository;

public class UserService {
    public UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
