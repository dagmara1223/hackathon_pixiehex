package com.pixiehex.kshipping.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SingleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;
    private String userEmail;

    private double originalPrice;
    private double depositAmount;
    private double finalPrice;
    private double remainingToPay;
    private double productWeight;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "group_order_id")
    private GroupOrder groupOrder;

    public enum OrderStatus {
        OPEN,
        LOCKED,
        PAID,
        CANCELLED
    }

    public SingleOrder() {
    }

    public SingleOrder(String productName, String userEmail, double originalPrice, double depositAmount, OrderStatus status) {
        this.productName = productName;
        this.userEmail = userEmail;
        this.originalPrice = originalPrice;
        this.depositAmount = depositAmount;
        this.status = status;
        this.orderDate = LocalDateTime.now();
        this.remainingToPay = 0.0;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getRemainingToPay() {
        return remainingToPay;
    }

    public void setRemainingToPay(double remainingToPay) {
        this.remainingToPay = remainingToPay;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public GroupOrder getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(GroupOrder groupOrder) {
        this.groupOrder = groupOrder;
    }
}