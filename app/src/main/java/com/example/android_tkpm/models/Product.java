package com.example.android_tkpm.models;

import com.example.android_tkpm.utils.ENV;

import java.util.List;

public class Product {
    private String _id, name, image, description;
    private Category category;
    private int price;
    private List<String> size, color;
    private Boolean favorite;
    private int quantity = 0;

    public Product() { }

    public Product(String _id, String name, String image) {
        this._id = _id;
        this.name = name;
        this.image = image;
    }

    public Product(String _id, String name, Category category, int price, String image) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = image;
    }

    public Product(String _id, String name, int price, String image, int quantity) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Product(String _id, String name, int price, String image, List<String> size, List<String> color, int quantity) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public Product(String _id, String name, String image, String description, Category category, int price, List<String> size, List<String> color) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.price = price;
        this.size = size;
        this.color = color;
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

    public String getImage() {
        return ENV.domain + "/images/" + image;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
