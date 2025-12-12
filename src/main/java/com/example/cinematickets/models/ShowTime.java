package com.example.cinematickets.models;

import java.time.LocalDateTime;
import java.util.List;

public class ShowTime {
    public int id;
    public LocalDateTime datetime;
    public boolean active;
    public int movieId;
    public int hallId;
    private List<Seat> seats;
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

}
