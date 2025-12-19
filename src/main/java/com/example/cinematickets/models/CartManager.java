package com.example.cinematickets.models;

import com.example.cinematickets.MyTicketsController;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final List<MyTicketsController.CheckoutTicket> cart = new ArrayList<>();

    public static void addToCart(MyTicketsController.CheckoutTicket ticket) {
        cart.add(ticket);
    }

    public static List<MyTicketsController.CheckoutTicket> getCart() {
        return cart;
    }

    public static void clearCart() {
        cart.clear();
    }

    public static double getTotalPrice() {
        return cart.stream()
                .mapToDouble(t -> t.getCheckout().getTotalAmount())
                .sum();
    }
}
