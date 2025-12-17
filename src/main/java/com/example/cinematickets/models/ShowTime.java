package org.example.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowTime implements Prototype{
    public int id;
    private LocalDateTime datetime;
    public boolean active;
    private int movieId;
    private int hallId;
    private List<Seat> seats=new ArrayList<>();
    public ShowTime(int id,LocalDateTime datetime,int movieId,int hallId,int hallCapacity){
        this.id=id;
        this.datetime=datetime;
        this.movieId=movieId;
        this.hallId=hallId;
        this.active=true;
        for (int i=1;i<=hallCapacity;i++){
            seats.add(new Seat(i,false));
        }
    }
    public int getAvailableSeatsNumber(){
        int count=0;
        for (Seat seat:seats){
            if(seat.isBooked)
                count++;
        }
        return count;
    }

    @Override
    public ShowTime getClone() {
        return new ShowTime(this.id,this.datetime,this.movieId,this.hallId,this.seats.size());
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


}
