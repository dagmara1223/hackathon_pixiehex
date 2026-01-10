package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Order;
import com.pixiehex.kshipping.model.Order;
import com.pixiehex.kshipping.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/")
    public ResponseEntity<Object> getOrder() {
        return ResponseEntity.ok(new Order());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById() {
        return ResponseEntity.ok(new Order());
    }

    @PostMapping("/")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody Order Order) {
        return ResponseEntity.ok(new Order());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody Order Order, @PathVariable int id) {
        return ResponseEntity.ok(new Order());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {
        return ResponseEntity.ok(new Order());
    }

}