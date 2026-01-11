package com.pixiehex.kshipping.dto;

import java.util.List;

public class CreateOrderRequest {

    private String userEmail;
    private String shippingAddress;
    private String phoneNumber;

    private List<BulkProductDTO> items;

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<BulkProductDTO> getItems() { return items; }
    public void setItems(List<BulkProductDTO> items) { this.items = items; }
}