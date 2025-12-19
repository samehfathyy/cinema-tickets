package com.example.cinematickets.models;

public class Paypal implements PaymentStrategy {
    public String pay(double amount){
        return "with paypal "+amount;
    }
}
