package com.example.cinematickets.models;

public class Seat implements Prototype{
    private int seatNumber;
    private boolean isBooked;
    public Seat(int seatNumber,boolean isBooked){
        this.seatNumber=seatNumber;
        this.isBooked=isBooked;
    }

    @Override
    public Seat getClone() {
        return new Seat(this.seatNumber,this.isBooked);
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }
    public boolean isAvailable() {
        return !isBooked;
    }
    public void bookSeat() {
        isBooked=true;
    }
}
