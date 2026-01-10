package com.pixiehex.kshipping.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double totalWeight;
    private double totalValue;

    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "groupOrder", cascade = CascadeType.ALL)
    private List<SingleOrder> orders = new ArrayList<>();

    public enum GroupStatus {
        PENDING,
        READY_TO_SHIP,
        SHIPPED
    }

    public GroupOrder() {}

    public GroupOrder(String name) {
        this.name = name;
        this.createdDate = LocalDateTime.now();
        this.status = GroupStatus.PENDING;
        this.totalWeight = 0;
        this.totalValue = 0;
    }

    public void addOrder(SingleOrder order) {
        orders.add(order);
        order.setGroupOrder(this);
        this.totalWeight += order.getProductWeight();
        this.totalValue += order.getOriginalPrice();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getTotalWeight() { return totalWeight; }
    public void setTotalWeight(double totalWeight) { this.totalWeight = totalWeight; }

    public double getTotalValue() { return totalValue; }
    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }

    public GroupStatus getStatus() { return status; }
    public void setStatus(GroupStatus status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public List<SingleOrder> getOrders() { return orders; }
    public void setOrders(List<SingleOrder> orders) { this.orders = orders; }
}