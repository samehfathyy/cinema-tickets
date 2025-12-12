package com.example.cinematickets.models;

import java.time.LocalDateTime;
import java.util.List;

public class ShowTime {
    public int id;
    public LocalDateTime datetime;
    public boolean active;
    public Movie movie;
    public Hall hall;
    public List<Seat> seats;
    public ShowTime(int id,LocalDateTime datetime,Movie movie,Hall hall,List<Seat> seats){
        this.id=id;
        this.datetime=datetime;
        this.movie=movie;
        this.hall=hall;
        this.active=true;
        if(seats==null){
            for (int i=1;i<=hall.capacity;i++){
                seats.add(new Seat(i,false));
            }
        }
        else {
            this.seats=seats;
        }
    }
}
