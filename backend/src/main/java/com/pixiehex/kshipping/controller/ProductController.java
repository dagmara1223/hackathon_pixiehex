package com.pixiehex.kshipping.controller;

import com.pixiehex.kshipping.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/")
    public List<Product> getProduct() {
        return;
    }

    @GetMapping("/{id}")
    public Product getProduct() {
        return;
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable int id) {
        return product;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        return;
    }

}
