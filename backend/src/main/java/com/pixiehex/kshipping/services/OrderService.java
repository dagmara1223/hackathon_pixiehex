package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.repository.SingleOrderRepository;

public class OrderService {
    public SingleOrderRepository singleOrderRepository;

    public OrderService(SingleOrderRepository singleOrderRepository){
        this.singleOrderRepository = singleOrderRepository;
    }
}
