package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.repository.OrderRepository;

public class OrderService {
    public OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
}
