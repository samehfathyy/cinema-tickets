package org.example.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowTime implements Prototype{
    public int id;
    private LocalDateTime datetime;
    private Float price;
    public boolean active;
    private int movieId;
    private int hallId;
    private List<Seat> seats=new ArrayList<>();
    public ShowTime(int id,LocalDateTime datetime,Float price,int movieId,int hallId,int hallCapacity){
        this.id=id;
        this.datetime=datetime;
        this.price=price;
        this.movieId=movieId;
        this.hallId=hallId;
        this.active=true;
            for (int i = 1; i <= hallCapacity; i++) {
                seats.add(new Seat(i, false));
            }
    }
    public ShowTime(int id,LocalDateTime datetime,Float price,int movieId,int hallId,int hallCapacity,List<Seat> seats){
        this.id=id;
        this.datetime=datetime;
        this.price=price;
        this.movieId=movieId;
        this.hallId=hallId;
        this.active=true;
        this.seats=seats;
    }
    public int getAvailableSeatsNumber(){
        int count=0;
        for (Seat seat:seats){
            if(seat.isBooked())
                count++;
        }
        return count;
    }

    public int bookSeat(int seatNumber){
        for (Seat seat:seats){
            if(seat.getSeatNumber()==seatNumber&&seat.isAvailable()){
                seat.bookSeat();
                return seat.getSeatNumber();
            }
        }
        return 0;
    }
    @Override
    public ShowTime getClone() {
        return new ShowTime(this.id,this.datetime,this.price,this.movieId,this.hallId,this.seats.size(),this.seats);
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }



    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getHallId() {
        return hallId;
    }

    public List<Seat> getSeats(){
        return seats.stream()
                .map(Seat::getClone)
                .collect(Collectors.toList());
    }


    public Float getPrice() {
        return price;
    }
}
