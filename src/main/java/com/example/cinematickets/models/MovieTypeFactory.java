package com.example.cinematickets.models;

import java.util.HashMap;
import java.util.Map;

public class MovieTypeFactory {
    private static final Map<String,FlyWeightMovieType> movieTypes = new HashMap<>();
    public static FlyWeightMovieType getMovieType(String language,String genre){
        if(movieTypes.get(language+genre)==null){
            movieTypes.put(language+genre,new FlyWeightMovieType(language,genre));
        }
        return movieTypes.get(language+genre);
    }
}
