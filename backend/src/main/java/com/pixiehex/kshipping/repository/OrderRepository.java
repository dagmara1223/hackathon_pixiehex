package com.pixiehex.kshipping.repository;

import com.pixiehex.kshipping.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
