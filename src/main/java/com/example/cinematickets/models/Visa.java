package com.example.cinematickets.models;

public class Visa implements PaymentStrategy {

    private String name;
    private String cardNumber;
    private String cvv;
    private String dateOfExpiry;

    public Visa(String name,String cardNumber,String cvv,String dateOfExpiry){
        this.name=name;
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.dateOfExpiry=dateOfExpiry;
    }
    public String pay(double amount){
        return "with visa "+amount;
    }
}
