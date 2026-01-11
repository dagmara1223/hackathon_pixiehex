package com.pixiehex.kshipping.services;

import com.pixiehex.kshipping.dto.CreateOrderRequest;
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

    // Te stałe są używane tylko w awaryjnym trybie closeCycle.
    // Główna logika jest w BatchService.
    private static final double SHARED_SHIPPING_COST = 15.00;
    private static final double VAT_RATE = 1.23;

    public SingleOrderService(SingleOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // --- NOWA METODA DO CHECKOUTU ---
    @Transactional
    public void updateContactDetailsForOpenOrders(String email, String address, String phone) {
        // Pobieramy wszystko co jest OPEN dla tego maila
        List<SingleOrder> cartItems = orderRepository.findByUserEmailAndStatus(email, OrderStatus.OPEN);

        if (cartItems.isEmpty()) {
            // Można rzucić błąd albo po prostu nic nie robić (logowanie)
            System.out.println("Brak otwartych zamówień dla: " + email);
            return;
        }

        // Nadpisujemy dane
        for (SingleOrder item : cartItems) {
            item.setShippingAddress(address);
            item.setPhoneNumber(phone);
        }
        orderRepository.saveAll(cartItems);
    }

    @Transactional
    public List<SingleOrder> createBulkOrders(CreateOrderRequest request) {
        List<SingleOrder> savedOrders = new java.util.ArrayList<>();
        for (com.pixiehex.kshipping.dto.BulkProductDTO item : request.getItems()) {
            SingleOrder order = new SingleOrder();
            order.setProductName(item.getProductName());
            order.setOriginalPrice(item.getPrice());
            order.setProductWeight(item.getWeight());
            order.setUserEmail(request.getUserEmail());
            order.setShippingAddress(request.getShippingAddress());
            order.setPhoneNumber(request.getPhoneNumber());

            double deposit = item.getPrice() * 0.30;
            order.setDepositAmount(deposit);
            order.setRemainingToPay(0);
            order.setStatus(OrderStatus.OPEN);
            order.setOrderDate(LocalDateTime.now());

            savedOrders.add(orderRepository.save(order));
        }
        return savedOrders;
    }

    public SingleOrder createPreorder(String productName, double price, double weight, String userEmail, String shippingAddress, String phoneNumber) {
        double deposit = price * 0.30;

        SingleOrder order = new SingleOrder();
        order.setProductName(productName);
        order.setUserEmail(userEmail);
        order.setShippingAddress(shippingAddress);
        order.setOriginalPrice(price);
        order.setProductWeight(weight);
        order.setDepositAmount(deposit);
        order.setPhoneNumber(phoneNumber);

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
        // TO JEST PROSTA LOGIKA. PAMIĘTAJ, ŻE BatchService MA LEPSZĄ.
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
        // Używamy metody sortującej, żeby najnowsze były u góry
        // Upewnij się, że masz tę metodę w Repository!
        return orderRepository.findByUserEmailContainingIgnoreCaseOrderByOrderDateDesc(userEmail);
    }

    public SingleOrder markOrderAsPaid(Long orderId) {
        SingleOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus(SingleOrder.OrderStatus.PAID);
        return orderRepository.save(order);
    }

    public void changeToLocked() {
    }

    public void changeUnpaidToCancelled() {
    }
}