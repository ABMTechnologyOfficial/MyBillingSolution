package com.abmtech.mybillingsolution.models;

import java.io.Serializable;

public class InventoryModel implements Serializable {
    String name, quantity, price, description, purchase_date, currentTime, user_id, product_id, vendorName, vendorMobile, vendorAddress, vendorGst;

    public InventoryModel(String name, String quantity, String price, String description,
                          String purchase_date, String currentTime, String user_id, String product_id,
                          String vendorName, String vendorMobile, String vendorAddress, String vendorGst) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.purchase_date = purchase_date;
        this.currentTime = currentTime;
        this.user_id = user_id;
        this.product_id = product_id;
        this.vendorName = vendorName;
        this.vendorMobile = vendorMobile;
        this.vendorAddress = vendorAddress;
        this.vendorGst = vendorGst;
    }

    public InventoryModel() {
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorMobile() {
        return vendorMobile;
    }

    public void setVendorMobile(String vendorMobile) {
        this.vendorMobile = vendorMobile;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorGst() {
        return vendorGst;
    }

    public void setVendorGst(String vendorGst) {
        this.vendorGst = vendorGst;
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
