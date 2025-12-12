package com.example.cinematickets.repos;

import com.example.cinematickets.models.Hall;
import com.example.cinematickets.models.Movie;
import com.example.cinematickets.models.ShowTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainRepository {
    private MainRepository repo;
    private MainRepository(){}
    public MainRepository getInstance() {
        if(repo==null){
            repo = new MainRepository();
        }
        return repo;
    }
    List<Movie> movies;
    List<ShowTime> showTimes;
    List<Hall> halls;
    void addMovie(Movie movie){
        movies.add(movie);
    }
    void deleteMovieById(int id){
        movies.removeIf(movie -> movie.id==id);
    }
    List<Movie> getMovies(){
        return movies;
    }
    List<Movie> getMoviesByRateHigherThan(Float rate){
        return movies.stream()
                .filter(movie -> movie.avgRate != null && movie.avgRate >= rate)
                .collect(Collectors.toList());
    }
    List<Movie> getMoviesByGenre(String genre){
        return movies.stream()
                .filter(movie -> movie.genre != null && movie.genre.equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
    List<Movie> getMoviesByLanguage(String language){
        return movies.stream()
                .filter(movie -> movie.language != null && movie.language.equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }

    void addHall(Hall hall){
        halls.add(hall);
    }
    List<Hall> getHalls(){
        return halls;
    }
    List<Movie> getMoviesByHall(int hallId){
        List<Movie> filteredMovies = new ArrayList<>();
        for(ShowTime showTime:showTimes){
            if(showTime.hall.id==hallId){
                filteredMovies.add(showTime.movie);
            }
        }
        return filteredMovies;
    }

    List<Movie> getMoviesByDate(LocalDateTime startDate,LocalDateTime endDate){
        //test for duplicated movies in the returned list *after test remove this line*
        if(startDate.isAfter(endDate)){
            return new ArrayList<>();
        }
        Set<Movie> filteredSet = new HashSet<>();
        for(ShowTime showTime:showTimes) {
            if(showTime.datetime.isAfter(startDate)&&showTime.datetime.isBefore(endDate)){
                filteredSet.add(showTime.movie);
            }
        }
        return new ArrayList<>(filteredSet);
    }

    }
