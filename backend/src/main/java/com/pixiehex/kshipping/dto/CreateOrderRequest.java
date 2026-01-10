package com.pixiehex.kshipping.dto; // lub .dto

import java.util.List;

public class CreateOrderRequest {
    private String userEmail;
    private String shippingAddress;
    private String phoneNumber;

    private List<ProductItem> items;

    public static class ProductItem {
        private String productName;
        private double price;
        private double weight;

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public double getWeight() { return weight; }
        public void setWeight(double weight) { this.weight = weight; }
    }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public List<ProductItem> getItems() { return items; }
    public void setItems(List<ProductItem> items) { this.items = items; }
}