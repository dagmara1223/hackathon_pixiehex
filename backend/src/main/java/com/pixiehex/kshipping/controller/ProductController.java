package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/")
    public ResponseEntity<Object> getProduct() {
        return;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct() {
        return;
    }

    @PostMapping("/")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        return product;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable int id) {
        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        return;
    }

}
