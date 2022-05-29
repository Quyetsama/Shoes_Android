package com.example.android_tkpm.models;

public class OrderHistory {
    private String _id, code, createdAt;
    private int quantity, total;

    public OrderHistory() {}

    public OrderHistory(String _id, String code, int quantity, int total, String createdAt) {
        this._id = _id;
        this.code = code;
        this.quantity = quantity;
        this.total = total;
        this.createdAt = createdAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
