package com.example.android_tkpm.models;

import java.util.List;

public class OrderRequest {
    private List<ItemOrder> orderList;
    private String name, phone, address;
    private int subTotal, shipping, total;

    public OrderRequest() {}

    public OrderRequest(List<ItemOrder> orderList, String name, String phone, String address, int subTotal, int shipping, int total) {
        this.orderList = orderList;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.subTotal = subTotal;
        this.shipping = shipping;
        this.total = total;
    }

    public List<ItemOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ItemOrder> orderList) {
        this.orderList = orderList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
