package com.example.android_tkpm.utils;

import com.example.android_tkpm.models.ItemCart;
import com.example.android_tkpm.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<ItemCart> cart = new ArrayList<>();

    public CartManager() { }

    public static List<ItemCart> getCart() {
        return cart;
    }

    public static void setCart(List<ItemCart> cart) {
        CartManager.cart = cart;
    }

    public static void addToCart(ItemCart itemCart) {
        for(int i = 0; i < CartManager.cart.size(); i++) {
            if(CartManager.cart.get(i).toString().equals(itemCart.toString())) {
                CartManager.cart.get(i).increaseQuantity();
                return;
            }
        }

        CartManager.cart.add(itemCart);
    }

    public static void removeFromCart(int index) {
        CartManager.cart.remove(index);
    }

    public static int getTotal() {
        int total = 0;
        for(ItemCart item : CartManager.cart) {
            total = total + (item.getPrice() * item.getQuantity());
        }

        return total;
    }

    public static void clearCart() {
        CartManager.cart.clear();
    }

}
