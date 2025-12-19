package com.example.cinematickets.models;

import java.util.List;

public class TotalPrice {
    public double calculateTotalPrice(List<Seat> seats, double seatPrice) {
        return seats.size() * seatPrice;
    }
}
