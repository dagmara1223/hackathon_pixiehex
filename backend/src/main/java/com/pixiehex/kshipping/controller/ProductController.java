package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import com.pixiehex.kshipping.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById() {
        return ResponseEntity.ok(new Product());
    }

    @PostMapping
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
