package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.SingleOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class SingleOrderController {

    @GetMapping("/")
    public ResponseEntity<Object> getOrder() {
        return ResponseEntity.ok(new SingleOrder());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById() {
        return ResponseEntity.ok(new SingleOrder());
    }

    @PostMapping("/")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody SingleOrder SingleOrder) {
        return ResponseEntity.ok(new SingleOrder());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody SingleOrder SingleOrder, @PathVariable int id) {
        return ResponseEntity.ok(new SingleOrder());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {
        return ResponseEntity.ok(new SingleOrder());
    }

}