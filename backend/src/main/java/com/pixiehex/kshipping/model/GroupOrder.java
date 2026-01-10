package com.pixiehex.kshipping.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GroupOrder {

    public Long getGroupOrderId() {
        return groupOrderId;
    }

    public void setGroupOrderId(Long orderId) {
        this.groupOrderId = orderId;
    }

    public List<SingleOrder> getOrders() {
        return singleOrders;
    }

    public void setOrders(List<SingleOrder> singleOrders) {
        this.singleOrders = singleOrders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ORDER_ID")
    private Long groupOrderId;


    @OneToMany(mappedBy = "groupOrder", cascade = CascadeType.ALL)
    private List<SingleOrder> singleOrders;
}

