package com.example.cinematickets.models;

import java.util.List;

public class Checkout {
    private String movieName;
    private String HallName;
    private int numberOfSeats;
    private List<Integer> seatsNumbers;
    private Float totalAmount;

    public Checkout(String movieName, String hallName, int numberOfSeats, List<Integer> seatsNumbers, Float totalAmount) {
        this.movieName = movieName;
        HallName = hallName;
        this.numberOfSeats = numberOfSeats;
        this.seatsNumbers = seatsNumbers;
        this.totalAmount = totalAmount;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getHallName() {
        return HallName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public List<Integer> getSeatsNumbers() {
        return seatsNumbers;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }
}
