package com.pixiehex.kshipping.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GroupOrder {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    @NotEmpty(message="List of orders cannot be empty")
    private List<Order> products;
}
