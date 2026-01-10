package com.pixiehex.kshipping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty @NotBlank String getName() {
        return name;
    }

    public void setName(@NotEmpty @NotBlank String name) {
        this.name = name;
    }

    @DecimalMin("0.1")
    public double getPrice() {
        return price;
    }

    public void setPrice(@DecimalMin("0.1") double price) {
        this.price = price;
    }

    @DecimalMin("0.1")
    public float getWeight() {
        return weight;
    }

    public void setWeight(@DecimalMin("0.1") float weight) {
        this.weight = weight;
    }

    @NotEmpty @NotBlank
    private String name;
    @DecimalMin("0.1")
    private double price;
    @DecimalMin("0.1")
    private float weight;
}
