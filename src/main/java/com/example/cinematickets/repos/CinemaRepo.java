package com.example.cinematickets.repos;

import com.example.cinematickets.models.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CinemaRepo {
    private CinemaRepo repo;
    private CinemaRepo(){}
    public CinemaRepo getInstance() {
        if(repo==null){
            repo = new CinemaRepo();
        }
        return repo;
    }
    List<User> users;
    boolean registerNewUser(User user){
        //search for username
        AtomicBoolean found= new AtomicBoolean(false);
        users.forEach(user1 -> {
            if(user1.getUsername().equals(user.getUsername()) ){
                found.set(true);
            }
        });
        if(found.get()){
            return false;
        }
        user.id=users.size();
        users.add(user);
        return true;
    }
    boolean login(String username,String password){
        AtomicBoolean found= new AtomicBoolean(false);
        users.forEach(user -> {
            if(user.getUsername()==username&&user.getPassword()==password){
                found.set(true);
            }
        });

        return found.get();
    }
}
