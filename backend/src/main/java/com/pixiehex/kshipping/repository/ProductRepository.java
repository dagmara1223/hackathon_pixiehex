package com.pixiehex.kshipping.repository;

import com.pixiehex.kshipping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
