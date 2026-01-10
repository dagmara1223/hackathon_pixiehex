package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.User;
import com.pixiehex.kshipping.model.User;
import com.pixiehex.kshipping.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok(new User());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        return ResponseEntity.ok(new User());
    }

    @PostMapping("/")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User User) {
        return ResponseEntity.ok(new User());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User User, @PathVariable int id) {
        return ResponseEntity.ok(new User());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(new User());
    }

}