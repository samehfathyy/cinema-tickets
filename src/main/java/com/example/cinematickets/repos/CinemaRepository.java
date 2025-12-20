package com.example.cinematickets.repos;

import com.example.cinematickets.models.*;

import java.util.List;

public interface CinemaRepository {
    // Movie operations
    List<Movie> getMovies();
    Movie getMovieById(int id);


    List<Movie> searchMoviesByName(String wordToSearchFor);
    List<Movie> filterMoviesByGenre(String genre);
    List<Movie> filterMoviesByLanguage(String language);
    // Hall operations
    Hall getHallById(int id);

    // ShowTime operations
    List<ShowTime> getShowTimes();

    // Booking operations
    Checkout bookMultipleTickets(int showTimeId, List<Integer> seatsNumbers);
}
