package com.example.cinematickets.models;

public class Paypal implements PaymentStrategy {

    private String phone;
    private String password;

    public Paypal(String phone,String password){
        this.phone=phone;
        this.password=password;
    }
    public String pay(double amount){
        return "with paypal "+amount;
    }
}
