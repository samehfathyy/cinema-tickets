package com.example.cinematickets.models;

public class Visa implements PaymentStrategy {
    public String pay(double amount){
        return "with visa "+amount;
    }
}
