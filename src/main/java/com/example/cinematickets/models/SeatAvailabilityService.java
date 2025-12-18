package com.example.cinematickets.models;

import java.util.List;

public class SeatAvailabilityService {
    public void ChangeSeatAvailability(List<Seat> seats){
        for (Seat seat : seats) {
            seat.bookSeat();
        }
    }
}
