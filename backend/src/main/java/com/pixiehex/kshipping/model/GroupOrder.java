package com.pixiehex.kshipping.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GroupOrder {

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<SingleOrder> getOrders() {
        return singleOrders;
    }

    public void setOrders(List<SingleOrder> singleOrders) {
        this.singleOrders = singleOrders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToMany(mappedBy = "groupOrder", cascade = CascadeType.ALL)
    private List<SingleOrder> singleOrders;

}
