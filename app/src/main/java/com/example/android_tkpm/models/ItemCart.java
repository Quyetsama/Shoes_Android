package com.example.android_tkpm.models;

public class ItemCart {
    private String _id, name, size, color, image;
    private int price, quantity;

    public ItemCart() {}

    public ItemCart(String _id, String name, String size, String color, String image, int price, int quantity) {
        this._id = _id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public String toString() {
        return _id + size + color;
    }

    public void increaseQuantity() {
        this.quantity = this.quantity + 1;
    }

    public void decreaseQuantity() {
        this.quantity = this.quantity - 1;
    }
}
