package com.example.cinematickets.models;

public class InstaPay implements PaymentStrategy {

    private String email;
    private String password;

    public InstaPay(String email,String password){
        this.email=email;
        this.password=password;
    }
    public String pay(double amount){
        return "with instapay "+amount;
    }
}
