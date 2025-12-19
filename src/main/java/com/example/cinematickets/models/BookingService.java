package com.example.cinematickets.models;

import java.util.List;

public class BookingService {

    private SeatAvailabilityService seatAvailability = new SeatAvailabilityService();
    private TotalPrice priceCalculator = new TotalPrice();
    private PaymentContext paymentContext = new PaymentContext();
    private PaymentStrategy paymentMethod = new Visa();

    List<Seat> selectedSeat = List.of(new Seat(1, true),
            new Seat(2, true));

    public void bookSeats() {
        double totalPrice = priceCalculator.calculateTotalPrice(selectedSeat, 100.0);

        paymentContext.setPaymentStrategy(paymentMethod);
        paymentContext.executePayment(totalPrice);
        seatAvailability.ChangeSeatAvailability(selectedSeat);
    }


}
