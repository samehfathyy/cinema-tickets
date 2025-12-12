package com.example.cinematickets.repos;

import com.example.cinematickets.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainRepository {
    private static MainRepository repo;
    private MainRepository(){}
    public static MainRepository getInstance() {
        if(repo==null){
            repo = new MainRepository();
        }
        return repo;
    }
    List<Movie> movies;
    List<ShowTime> showTimes;
    List<Hall> halls;
    int moviesCount=0;
    int hallsCount=0;
    int showTimesCount=0;

    //movie
    void addMovie(String name,String language,String genre){
        FlyWeightMovieType movieType = MovieTypeFactory.getMovieType(language,genre);
        movies.add(new Movie(moviesCount++, name,movieType));
    }
    void deleteMovieById(int id){
        movies.removeIf(movie -> movie.id==id);
    }
    Movie getMovieById(int id){
        for (Movie movie:movies){
            if (movie.id==id)
                return movie;
        }
        return null;
    }
    List<Movie> getMovies(){
        return new ArrayList<>(movies);
    }
    List<Movie> getMoviesByRateHigherThan(Float rate){
        return movies.stream()
                .filter(movie -> movie.avgRate != null && movie.avgRate >= rate)
                .collect(Collectors.toList());
    }
    List<Movie> getMoviesByGenre(String genre){
        return movies.stream()
                .filter(movie -> movie.type.genre != null && movie.type.genre.equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
    List<Movie> getMoviesByLanguage(String language){
        return movies.stream()
                .filter(movie -> movie.type.language != null && movie.type.language.equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }
    List<Movie> getMoviesByHall(int hallId){
        List<Movie> filteredMovies = new ArrayList<>();
        try {
            for(ShowTime showTime:showTimes){
                if(showTime.hallId==hallId){
                    filteredMovies.add(getMovieById( showTime.movieId));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filteredMovies;
    }
    List<Movie> getMoviesByDate(LocalDateTime startDate,LocalDateTime endDate){
        //test for duplicated movies in the returned list *after test remove this line*
        if(startDate.isAfter(endDate)){
            return new ArrayList<>();
        }
        Set<Movie> filteredSet = new HashSet<>();

        try {
            for(ShowTime showTime:showTimes) {
                if(showTime.datetime.isAfter(startDate)&&showTime.datetime.isBefore(endDate)){
                    filteredSet.add(getMovieById( showTime.movieId));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(filteredSet);
    }

    //halls
    void addHall(String name,int capacity){
        halls.add(new Hall(hallsCount++,name,capacity));
    }
    List<Hall> getHalls(){
        return new ArrayList<>(halls);
    }
    Hall getHallById(int id){
        for (Hall hall:halls){
            if (hall.id==id)
                return hall;
        }
        return null;
    }

    //showTimes
    public boolean addShowTime(LocalDateTime date,int movieId,int hallId,int capacity){
        if (getHallById(hallId)==null||getMovieById(movieId)==null){
            return false;
        }
        showTimes.add(new ShowTime(showTimesCount++,date,movieId,hallId,capacity));
        return true;
    }


    }
