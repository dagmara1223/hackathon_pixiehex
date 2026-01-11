package com.pixiehex.kshipping.repository;

import com.pixiehex.kshipping.model.SingleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SingleOrderRepository extends JpaRepository<SingleOrder, Long> {
    List<SingleOrder> findByStatus(SingleOrder.OrderStatus status);
    List<SingleOrder> findByUserEmail(String userEmail);

    List<SingleOrder> findByUserEmailContainingIgnoreCaseOrderByOrderDateDesc(String userEmail);

    List<SingleOrder> findByUserEmailAndStatus(String email, SingleOrder.OrderStatus orderStatus);
}