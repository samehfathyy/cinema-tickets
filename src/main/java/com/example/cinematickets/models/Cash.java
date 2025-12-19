package com.example.cinematickets.models;

public class Cash implements PaymentStrategy {
    public String pay(double amount){
        return "with cash "+amount;
    }
}
