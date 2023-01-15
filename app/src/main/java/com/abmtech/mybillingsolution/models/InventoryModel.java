package com.abmtech.mybillingsolution.models;

public class InventoryModel {
    String name, quantity, price, description, purchase_date, currentTime, user_id, product_id;

    public InventoryModel(String name, String quantity, String price, String description, String purchase_date, String currentTime, String user_id, String product_id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.purchase_date = purchase_date;
        this.currentTime = currentTime;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public InventoryModel() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
