package com.cspl.tourtravelapps.model;

/**
 * Created by a_man on 24-01-2018.
 */

public class CartItem {
    private String name;
    private int quantity, imageRes;
    private float price, priceTotal;

    public CartItem(String name, int quantity, float price, int imageRes) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageRes = imageRes;
        this.priceTotal = this.price * this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.priceTotal = this.price * this.quantity;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public float getPriceTotal() {
        return priceTotal;
    }
}
