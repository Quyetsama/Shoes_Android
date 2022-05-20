package com.example.android_tkpm.models;

public class ItemOrder {
    private String product;
    private int size;
    private String color;
    private int price;
    private int quantity;

    public ItemOrder() {}

    public ItemOrder(String product, int size, String color, int price, int quantity) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
