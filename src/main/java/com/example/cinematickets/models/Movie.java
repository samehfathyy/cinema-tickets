package com.example.cinematickets.models;

import java.util.List;

public class Movie {
    public int id;
    public String name;
    public String language;
    public String genre;
    public Float avgRate;
    private List<Review> reviews;
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

}
