package com.example.dev.styleomega.Model;

import java.util.ArrayList;

/**
 * Created by Dev on 8/18/2017.
 */

public class productDetails {

    private String email;
    private String productID;
    private String name;
    private String qty;
    private String price;
    private String image;
    private String description;
    private String size;
    private String totalPrice;

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email= email;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotalPrice() { return totalPrice; }

    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }



}
