package org.example.repos;



import org.example.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainRepository {
    private static MainRepository repo;
    private MainRepository(){
    }
    public static MainRepository getInstance() {
        if(repo==null){
            repo = new MainRepository();
        }
        return repo;
    }
    List<Movie> movies=new ArrayList<>();
    List<ShowTime> showTimes=new ArrayList<>();
    List<Hall> halls=new ArrayList<>();
    int moviesCount=0;
    int hallsCount=0;
    int showTimesCount=0;

    //movie
    public void addMovie(String name,String language,String genre){
        FlyWeightMovieType movieType = MovieTypeFactory.getMovieType(language,genre);
        movies.add(new Movie(moviesCount++, name,movieType));
    }
    public void deleteMovieById(int id){
        movies.removeIf(movie -> movie.id==id);
    }
    public Movie getMovieById(int id){
        for (Movie movie:movies){
            if (movie.id==id)
                return movie;
        }
        return null;
    }

    public List<Movie> getMovies(){
        return movies.stream()
                .map(Movie::getClone)
                .collect(Collectors.toList());
    }
    public List<Movie> getMoviesByRateHigherThan(Float rate){
        return movies.stream()
                .filter(movie -> movie.avgRate != null && movie.avgRate >= rate)
                .collect(Collectors.toList());
    }
    public List<Movie> filterMoviesByGenre(String genre){
        return movies.stream()
                .filter(movie -> movie.type.genre != null )
                .filter(movie ->  movie.type.genre.equalsIgnoreCase(genre))
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Movie> filterMoviesByLanguage(String language){
        return movies.stream()
                .filter(movie -> movie.type.language != null )
                .filter(movie ->  movie.type.language.equalsIgnoreCase(language))
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Movie> filterMoviesByHallId(int hallId){
        List<Movie> filteredMovies = new ArrayList<>();
        try {
            for(ShowTime showTime:showTimes){
                if(showTime.getHallId()==hallId){
                    filteredMovies.add(getMovieById( showTime.getMovieId()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filteredMovies;
    }
    public List<Movie> filterMoviesByDate(LocalDateTime startDate,LocalDateTime endDate){
        //test for duplicated movies in the returned list *after test remove this line*
        if(startDate.isAfter(endDate)){
            return new ArrayList<>();
        }
        Set<Movie> filteredSet = new HashSet<>();
        try {
            for(ShowTime showTime:showTimes) {
                if(showTime.getDatetime().isAfter(startDate)&&showTime.getDatetime().isBefore(endDate)){
                    filteredSet.add(getMovieById( showTime.getMovieId()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(filteredSet);
    }
    public List<Movie> searchMoviesByName(String wordToSearchFor) {
        if (wordToSearchFor == null || wordToSearchFor.isBlank()) {
            return new ArrayList<>();
        }
        String keyword = wordToSearchFor.toLowerCase();
        return movies.stream()
                .filter(movie -> movie.getName() != null)
                .filter(movie -> movie.getName().toLowerCase().contains(keyword))
                .distinct() //removes duplicates
                .collect(Collectors.toList()); //Ends the stream and turns it into a collection
    }

    public List<String> getLanguagesFromAllMovies(){
        List<String> languages = new ArrayList<>();
        for (Movie movie:movies){
            languages.add(movie.type.language);
        }
        return languages.stream().distinct().collect(Collectors.toList());
    }
    //halls
    public void addHall(String name,int capacity){
        halls.add(new Hall(hallsCount++,name,capacity));
    }
    public List<Hall> getHalls(){
        return halls.stream()
                .map(Hall::getClone)
                .collect(Collectors.toList());
    }
    public Hall getHallById(int id){
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
    public List<ShowTime> getShowTimes(){
        return showTimes.stream()
                .map(ShowTime::getClone)
                .collect(Collectors.toList());
    }


    }
