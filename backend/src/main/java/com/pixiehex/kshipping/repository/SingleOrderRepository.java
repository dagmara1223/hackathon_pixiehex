package com.pixiehex.kshipping.repository;

import com.pixiehex.kshipping.model.SingleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleOrderRepository extends JpaRepository<SingleOrder, Long> {
}

