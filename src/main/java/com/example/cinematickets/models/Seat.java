package org.example.models;

public class Seat implements Prototype{
    public int seatNumber;
    public boolean isBooked;
    public Seat(int seatNumber,boolean isBooked){
        this.seatNumber=seatNumber;
        this.isBooked=isBooked;
    }

    @Override
    public Seat getClone() {
        return new Seat(this.seatNumber,this.isBooked);
    }
}
