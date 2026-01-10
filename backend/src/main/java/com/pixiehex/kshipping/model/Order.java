package com.pixiehex.kshipping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Order {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @NotNull
    private int customerId;
    @NotEmpty(message = "Product list cannot be empty")
    private List<Product> products;


}
