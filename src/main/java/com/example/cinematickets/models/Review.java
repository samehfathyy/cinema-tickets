package com.example.cinematickets.models;
public class Review {
    String comment;
    Float rate;

    public Review(String comment, Float rate) {
        this.comment = comment;
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public Float getRate() {
        return rate;
    }
}
