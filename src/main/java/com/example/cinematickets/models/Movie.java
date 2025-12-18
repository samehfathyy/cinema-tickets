package com.example.cinematickets.models;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Prototype{
    public int id;
    private String name;
    public FlyWeightMovieType type;
    public Float avgRate;
    private List<Review> reviews;

    public Movie(int id, String name, FlyWeightMovieType type, Float avgRate, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.avgRate = avgRate;
        this.reviews = reviews;
    }
    public Movie(int id, String name, FlyWeightMovieType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.avgRate = 0.0F;
        this.reviews = new ArrayList<>();
    }

    void addReview(Review r){
        reviews.add(r);
        Float sum= 0.0F;
        for (Review review:reviews){
            sum =+ review.rate;
        }
        avgRate=sum/reviews.size();
    }
    List<Review> getReviews(){
        return reviews;
    }

    @Override
    public Movie getClone() {
        return new Movie(this.id,this.name,this.type,this.avgRate,this.reviews);
    }

    public String getName() {
        return name;
    }
}
