package com.example.cinematickets.repos;

import com.example.cinematickets.models.*;

import java.util.List;

/**
 * Proxy pattern implementation for CinemaRepository
 * This proxy provides a layer of abstraction between controllers and the main repository,
 * allowing for additional functionality like caching, logging, or access control in the future.
 */
public class CinemaRepositoryProxy implements CinemaRepository {

    private final MainRepository realRepository;

    public CinemaRepositoryProxy() {
        this.realRepository = MainRepository.getInstance();
    }

    @Override
    public List<Movie> getMovies() {
        System.out.println("Proxy: Accessing getMovies()");
        return realRepository.getMovies();
    }

    @Override
    public Movie getMovieById(int id) {
        System.out.println("Proxy: Accessing getMovieById(" + id + ")");
        return realRepository.getMovieById(id);
    }

    @Override
    public Hall getHallById(int id) {
        System.out.println("Proxy: Accessing getHallById(" + id + ")");
        return realRepository.getHallById(id);
    }

    @Override
    public List<ShowTime> getShowTimes() {
        System.out.println("Proxy: Accessing getShowTimes()");
        return realRepository.getShowTimes();
    }

    @Override
    public Checkout bookMultipleTickets(int showTimeId, List<Integer> seatsNumbers) {
        System.out.println("Proxy: Accessing bookMultipleTickets(" + showTimeId + ", " + seatsNumbers.size() + " seats)");
        return realRepository.bookMultipleTickets(showTimeId, seatsNumbers);
    }
}
