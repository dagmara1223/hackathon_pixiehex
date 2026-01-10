package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/")
    public ResponseEntity<Object> getProduct() {
        return ResponseEntity.ok(new Product());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct() {
        return ResponseEntity.ok(new Product());
    }

    @PostMapping("/")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(new Product());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody Product product, @PathVariable int id) {
        return ResponseEntity.ok(new Product());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        return ResponseEntity.ok(new Product());
    }

}
