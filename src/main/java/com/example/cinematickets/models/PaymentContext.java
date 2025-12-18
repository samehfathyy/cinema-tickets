package com.example.cinematickets.models;

public class PaymentContext {
    private PaymentStrategy payment;

    public void setPaymentStrategy(PaymentStrategy payment){
        this.payment = payment;
    }

    public String executePayment(double amount) {
        return payment.pay(amount);
    }
}
