package com.pixiehex.kshipping.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class SingleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;
    private String userEmail;

    // --- NOWE POLE ---
    private String shippingAddress;

    private double originalPrice;
    private double depositAmount;
    private double finalPrice;
    private double remainingToPay;
    private double productWeight;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "group_order_id")
    @JsonIgnoreProperties("orders")
    private GroupOrder groupOrder;

    public enum OrderStatus {
        OPEN, LOCKED, PAID, CANCELLED, ACCEPT
    }

    public SingleOrder() {
    }

    public SingleOrder(String productName, String userEmail, String shippingAddress, double originalPrice, double depositAmount, OrderStatus status, String phoneNumber) {
        this.productName = productName;
        this.userEmail = userEmail;
        this.shippingAddress = shippingAddress;
        this.originalPrice = originalPrice;
        this.phoneNumber = phoneNumber;
        this.depositAmount = depositAmount;
        this.status = status;
        this.orderDate = LocalDateTime.now();
        this.remainingToPay = 0.0;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // --- RESZTA GETTERÓW I SETTERÓW BEZ ZMIAN ---
    public double getProductWeight() { return productWeight; }
    public void setProductWeight(double productWeight) { this.productWeight = productWeight; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(double depositAmount) { this.depositAmount = depositAmount; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public double getRemainingToPay() { return remainingToPay; }
    public void setRemainingToPay(double remainingToPay) { this.remainingToPay = remainingToPay; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public GroupOrder getGroupOrder() { return groupOrder; }
    public void setGroupOrder(GroupOrder groupOrder) { this.groupOrder = groupOrder; }
}