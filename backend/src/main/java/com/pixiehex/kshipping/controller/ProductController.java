package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import com.pixiehex.kshipping.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*") // <--- WAŻNE NA HACKATHON: Pozwala frontendowi (np. localhost:3000) gadać z backendem
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        // NAPRAWIONE: Zwracamy listę, a nie pusty obiekt
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) { // Zmieniłem int na Long
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        // NAPRAWIONE: Zapisujemy produkt
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // Opcjonalnie: delete na potrzeby demo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}