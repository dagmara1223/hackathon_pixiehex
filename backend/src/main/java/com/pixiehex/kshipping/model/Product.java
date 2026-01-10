package com.pixiehex.kshipping.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Product {
    private int id;
    @NotEmpty @NotBlank
    private String name;
    @DecimalMin("0.1")
    private double price;
    @DecimalMin("0.1")
    private float weight;
}
