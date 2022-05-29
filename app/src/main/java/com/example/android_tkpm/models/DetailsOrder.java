package com.example.android_tkpm.models;

import java.util.List;

public class DetailsOrder {
    private String _id, code, name, phone, address, deliveryTime;
    private int status, subTotal, shipping, total;
    private List<OrderInfo> ordersInfo;

    public DetailsOrder() {}

    public DetailsOrder(String _id, String code, String name, String phone, String address, String deliveryTime, int status, int subTotal, int shipping, int total, List<OrderInfo> ordersInfo) {
        this._id = _id;
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.status = status;
        this.subTotal = subTotal;
        this.shipping = shipping;
        this.total = total;
        this.ordersInfo = ordersInfo;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public List<OrderInfo> getOrdersInfo() {
        return ordersInfo;
    }

    public void setOrdersInfo(List<OrderInfo> ordersInfo) {
        this.ordersInfo = ordersInfo;
    }
}
