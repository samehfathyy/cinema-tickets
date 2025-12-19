package com.example.cinematickets.models;

import com.example.cinematickets.MyTicketsController;

import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private static final List<MyTicketsController.CheckoutTicket> tickets = new ArrayList<>();

    public static void addTicket(MyTicketsController.CheckoutTicket ticket) {
        tickets.add(ticket);
    }

    public static List<MyTicketsController.CheckoutTicket> getTickets() {
        return new ArrayList<>(tickets);
    }
}
