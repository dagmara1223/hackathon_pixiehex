package com.pixiehex.kshipping.services;

import java.util.List;

import com.pixiehex.kshipping.model.Product;
import com.pixiehex.kshipping.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
