package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.model.SingleOrder;
import com.pixiehex.kshipping.model.SingleOrder.OrderStatus;
import com.pixiehex.kshipping.repository.SingleOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SingleOrderService {

    private final SingleOrderRepository orderRepository;

    private static final double SHARED_SHIPPING_COST = 15.00;
    private static final double VAT_RATE = 1.23;

    public SingleOrderService(SingleOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public SingleOrder createPreorder(String productName, double price, double weight, String userEmail, String shippingAddress) {

        double deposit = price * 0.30;

        SingleOrder order = new SingleOrder();
        order.setProductName(productName);
        order.setUserEmail(userEmail);
        order.setShippingAddress(shippingAddress); // <--- Zapisujemy adres
        order.setOriginalPrice(price);
        order.setProductWeight(weight); // Zakładam, że wagę też przesyłasz
        order.setDepositAmount(deposit);
        order.setRemainingToPay(0);
        order.setStatus(OrderStatus.OPEN);
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public void cancelPreorder(Long id) {
        SingleOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zamówienie nie istnieje"));

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new IllegalStateException("Za późno! Preorder zamknięty. Zaliczka przepadła.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public List<SingleOrder> closeCycleAndCalculateCosts() {
        List<SingleOrder> openOrders = orderRepository.findByStatus(OrderStatus.OPEN);

        for (SingleOrder order : openOrders) {
            double baseTotal = order.getOriginalPrice() + SHARED_SHIPPING_COST;
            double totalWithTax = baseTotal * VAT_RATE;

            double remaining = totalWithTax - order.getDepositAmount();

            order.setFinalPrice(Math.round(totalWithTax * 100.0) / 100.0);
            order.setRemainingToPay(Math.round(remaining * 100.0) / 100.0);
            order.setStatus(OrderStatus.LOCKED);
        }

        return orderRepository.saveAll(openOrders);
    }

    public List<SingleOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<SingleOrder> getOrdersByUserEmail(String userEmail) {
        return orderRepository.findByUserEmail(userEmail);
    }
}